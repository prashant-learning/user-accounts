package com.usbank.user.accounts.useraccounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.usbank.user.accounts.useraccounts.model.Accounts;
import com.usbank.user.accounts.useraccounts.model.request.AccountCreateRequest;
import com.usbank.user.accounts.useraccounts.model.response.UserAccountDetailsWithLoanAccountResponse;
import com.usbank.user.accounts.useraccounts.service.UserAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserAccountController implements UserAccountApi{

    @Autowired
    private UserAccountsService userAccountsService;

    @Override
    @Tag(name = "user accounts")
    @Operation(summary = "Used for creating user account", description = "")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201", description = "Successful created the user accounts"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PostMapping("/accounts")
    public ResponseEntity<Accounts> createUserAccount(@RequestBody AccountCreateRequest accountCreateRequest) throws JsonProcessingException {
       return ResponseEntity.status(HttpStatus.CREATED).body(userAccountsService.createUserAccounts(accountCreateRequest));
    }

    @Override
    @Tag(name = "user accounts")
    @Operation(summary = "Used for fetching user account", description = "")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201", description = "Successful fetched the user accounts"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping("/accounts/{userId}")
    public ResponseEntity<List<Accounts>> getUserAccount(@PathVariable long userId) {
       return ResponseEntity.status(HttpStatus.OK).body(userAccountsService.getAccountsByUserId(userId));
    }

    @Override
    @Tag(name = "user accounts")
    @Operation(summary = "Used for fetching user account with loan", description = "")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "201", description = "Successful fetched the user accounts"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping("/accounts/loan/{userId}")
    public ResponseEntity<List<UserAccountDetailsWithLoanAccountResponse>> getUserAccountWithLoanDetails(@PathVariable long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userAccountsService.getUserAccountWithLoanDetails(userId));
    }
}
