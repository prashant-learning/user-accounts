package com.usbank.user.accounts.useraccounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.usbank.user.accounts.useraccounts.model.Accounts;
import com.usbank.user.accounts.useraccounts.model.request.AccountCreateRequest;
import com.usbank.user.accounts.useraccounts.model.response.UserAccountDetailsWithLoanAccountResponse;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserAccountApi {

    public ResponseEntity<Accounts> createUserAccount(AccountCreateRequest accountCreateRequest) throws JsonProcessingException;
    public ResponseEntity<List<Accounts>> getUserAccount(long userId);

    public ResponseEntity<List<UserAccountDetailsWithLoanAccountResponse>> getUserAccountWithLoanDetails(long userId);
}
