package com.usbank.user.accounts.useraccounts.advice;

import com.usbank.user.accounts.useraccounts.exception.AccountCreationFailedException;
import com.usbank.user.accounts.useraccounts.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AccountCreationFailedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAccountCreationFailedException(AccountCreationFailedException exception) {

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), "Failed to create the account", HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }


}
