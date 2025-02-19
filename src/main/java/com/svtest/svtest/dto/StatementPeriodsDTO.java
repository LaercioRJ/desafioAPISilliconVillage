package com.svtest.svtest.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class StatementPeriodsDTO {
    @NotNull
    long accountId;

    @NotNull
    LocalDate startDate;

    @NotNull
    LocalDate endDate;

    public long getAccountId() {
        return accountId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
