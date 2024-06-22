package com.eazybytes.accounts.dto;

import lombok.Data;

@Data
public class LoansDto {
    private String loanType;

    private int totalLoan;

    private int amountPaid;

    private int outstandingAmount;
}
