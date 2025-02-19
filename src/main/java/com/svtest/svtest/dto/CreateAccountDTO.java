package com.svtest.svtest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CreateAccountDTO {
    @NotNull(message = "A user must be informed!")
    Long userId;
    
    @NotNull(message = "The account balance must be informed!")
    @Positive(message = "The account balance should be 0 or higher!")
    double balance;
    
    @NotNull(message = "A withdraw limit must be informed!")
    double withdrawLimit;
    
    @NotNull(message = "Account type must be informed!")
    String accountType;

    public String getAccountType() {
        return this.accountType;
    }

    public double getBalance() {
        return this.balance;
    }

    public double getWithdrawLimit() {
        return this.withdrawLimit;
    }

    public Long getUserId() {
        return this.userId;
    }
}
