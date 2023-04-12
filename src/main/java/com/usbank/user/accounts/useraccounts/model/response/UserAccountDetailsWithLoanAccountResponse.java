package com.usbank.user.accounts.useraccounts.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties
public class UserAccountDetailsWithLoanAccountResponse {

    private long userId;
    private String accountId;
    private LocalDate openingDate;
    private String accountType;
    private boolean isMinor;
    private String branchName;
    private String branchLocation;
    private String branchIFSC;

    private LoanAccountDetailsResponse loanAccountDetailsResponse;
}
