package com.usbank.user.accounts.useraccounts.controller;

import com.usbank.user.accounts.useraccounts.model.Accounts;
import com.usbank.user.accounts.useraccounts.model.request.AccountCreateRequest;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserAccountApi {

    public ResponseEntity<Accounts> createUserAccount(AccountCreateRequest accountCreateRequest);
    public ResponseEntity<List<Accounts>> getUserAccount(long userId);
}
