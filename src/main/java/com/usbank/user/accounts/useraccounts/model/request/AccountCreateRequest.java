package com.usbank.user.accounts.useraccounts.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountCreateRequest {

    private long userId;
    private LocalDate openingDate;
    private String accountType; // joint account or single account
    private boolean isMinor;
    private String branchName;
    private String branchLocation;
    private String branchIFSC;
    private LoanApplicationRequest loanApplicationRequest;
}
