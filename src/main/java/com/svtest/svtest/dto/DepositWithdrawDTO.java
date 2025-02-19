package com.svtest.svtest.dto;

import javax.validation.constraints.NotNull;

public class DepositWithdrawDTO {
    @NotNull
    long accountId;

    @NotNull
    double value;

    public double getValue() {
        return value;
    }

    public long getAccountId() {
        return accountId;
    }
}
