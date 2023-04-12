package com.usbank.user.accounts.useraccounts.model.request;

import lombok.Data;

@Data
public class LoanApplicationRequest {

    private String loanType;
    private String loanStatus;
    private boolean needsApproval;
    private double loanAmount;
    private double interestRate;
    private double emi;
    private double timeframe;
    private String applicantOccupation;
    private int creditScore;

}
