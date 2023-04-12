package com.usbank.user.accounts.useraccounts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.usbank.user.accounts.useraccounts.exception.AccountCreationFailedException;
import com.usbank.user.accounts.useraccounts.model.Accounts;
import com.usbank.user.accounts.useraccounts.model.request.AccountCreateRequest;
import com.usbank.user.accounts.useraccounts.model.response.LoanAccountDetailsResponse;
import com.usbank.user.accounts.useraccounts.model.response.UserAccountDetailsWithLoanAccountResponse;
import com.usbank.user.accounts.useraccounts.repository.UserAccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserAccountsService {

    @Autowired
    private UserAccountsRepository userAccountsRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Accounts createUserAccounts(AccountCreateRequest accountCreateRequest) throws JsonProcessingException {

        Accounts accounts = new Accounts();
        accounts.setUserId(accountCreateRequest.getUserId());
        accounts.setAccountId(UUID.randomUUID().toString());
        accounts.setAccountType(accountCreateRequest.getAccountType());
        accounts.setMinor(accountCreateRequest.isMinor());
        accounts.setBranchName(accountCreateRequest.getBranchName());
        accounts.setBranchIFSC(accountCreateRequest.getBranchIFSC());
        accounts.setBranchLocation(accountCreateRequest.getBranchLocation());
        accounts.setOpeningDate(accountCreateRequest.getOpeningDate());


        Accounts savedAccount = userAccountsRepository.save(accounts);

        try {
            if (accountCreateRequest.getAccountType().equalsIgnoreCase("loan")) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                String url = "http://localhost:8075/api/v1/loan/application";

                Map<String, String> map = new HashMap<>();

                Map<String, String> bodyParamMap = new HashMap<String, String>();

                //Set your request body params
                bodyParamMap.put("loanType", accountCreateRequest.getLoanApplicationRequest().getLoanType());
                bodyParamMap.put("loanStatus", accountCreateRequest.getLoanApplicationRequest().getLoanStatus());
                bodyParamMap.put("needsApproval", String.valueOf(accountCreateRequest.getLoanApplicationRequest().isNeedsApproval()));
                bodyParamMap.put("loanAmount", String.valueOf(accountCreateRequest.getLoanApplicationRequest().getLoanAmount()));
                bodyParamMap.put("interestRate", String.valueOf(accountCreateRequest.getLoanApplicationRequest().getInterestRate()));
                bodyParamMap.put("emi", String.valueOf(accountCreateRequest.getLoanApplicationRequest().getEmi()));
                bodyParamMap.put("timeframe", String.valueOf(accountCreateRequest.getLoanApplicationRequest().getTimeframe()));
                bodyParamMap.put("applicantOccupation", accountCreateRequest.getLoanApplicationRequest().getApplicantOccupation());
                bodyParamMap.put("creditScore", String.valueOf(accountCreateRequest.getLoanApplicationRequest().getCreditScore()));
                bodyParamMap.put("accountId", savedAccount.getAccountId().toString());

                String reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);

                HttpEntity<String> requestEnty = new HttpEntity<>(reqBodyData, headers);

                //  HttpEntity<?> httpEntity = new HttpEntity<>(headers);

                ResponseEntity<Integer> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEnty,
                        Integer.class, map);

                int loanApplicationId = responseEntity.getBody();
            }
        } catch (Exception exception) {
            userAccountsRepository.deleteById(String.valueOf(savedAccount.getId()));
            throw new AccountCreationFailedException("Failed to create account due to loan application");
        }

        return savedAccount;
    }

    public List<Accounts> getAccountsByUserId(long userId) {

        return userAccountsRepository.findByUserId(userId);
    }


    public List<UserAccountDetailsWithLoanAccountResponse> getUserAccountWithLoanDetails(long userId) {

        System.out.println(userId);

        List<Accounts> accountsByUserId = userAccountsRepository.findByUserId(userId);

        System.out.println(accountsByUserId.size());

         return accountsByUserId.stream().map(accounts -> {
           // LoanAccountDetailsResponse loanAccountDetails = getLoanAccountDetails(accounts.getAccountId());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String jsonString = null;
            UserAccountDetailsWithLoanAccountResponse userAccountDetailsWithLoanAccountResponse = null;
            try {
                jsonString = mapper.writeValueAsString(accounts);
                userAccountDetailsWithLoanAccountResponse  = mapper.readValue(jsonString, UserAccountDetailsWithLoanAccountResponse.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            LoanAccountDetailsResponse fetchedLoanAccountDetails = getLoanAccountDetails(accounts.getAccountId());
            if(fetchedLoanAccountDetails != null){
                userAccountDetailsWithLoanAccountResponse.setLoanAccountDetailsResponse(fetchedLoanAccountDetails);
            }
            return userAccountDetailsWithLoanAccountResponse;
        }).collect(Collectors.toList());
    }

    private LoanAccountDetailsResponse getLoanAccountDetails(String accountId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://localhost:8075/api/v1/loan/loan/{accountId}";

        Map<String, String> map = new HashMap<>();
        map.put("accountId", accountId);
        HttpEntity<String> requestEnty = new HttpEntity<>(headers);

        //  HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<LoanAccountDetailsResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEnty,
                    LoanAccountDetailsResponse.class, map);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                return responseEntity.getBody();
            }else {
                return null;
            }
        }catch (HttpClientErrorException exception){
            return null;
        }
    }
}
