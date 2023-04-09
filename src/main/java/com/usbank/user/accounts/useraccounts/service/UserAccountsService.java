package com.usbank.user.accounts.useraccounts.service;

import com.usbank.user.accounts.useraccounts.model.Accounts;
import com.usbank.user.accounts.useraccounts.model.request.AccountCreateRequest;
import com.usbank.user.accounts.useraccounts.repository.UserAccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAccountsService {

    @Autowired
    private UserAccountsRepository userAccountsRepository;

    public Accounts createUserAccounts(AccountCreateRequest accountCreateRequest){

        Accounts accounts = new Accounts();
        accounts.setUserId(accountCreateRequest.getUserId());
        accounts.setAccountId(UUID.randomUUID().toString());
        accounts.setAccountType(accountCreateRequest.getAccountType());
        accounts.setMinor(accountCreateRequest.isMinor());
        accounts.setBranchName(accountCreateRequest.getBranchName());
        accounts.setBranchIFSC(accountCreateRequest.getBranchIFSC());
        accounts.setBranchLocation(accountCreateRequest.getBranchLocation());
        accounts.setOpeningDate(accountCreateRequest.getOpeningDate());

       return userAccountsRepository.save(accounts);
    }

    public List<Accounts> getAccountsByUserId(long userId){

       return userAccountsRepository.findByUserId(userId);
    }
}
