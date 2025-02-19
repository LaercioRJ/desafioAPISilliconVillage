package com.svtest.svtest.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "accounts")
public class Account extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;*/

    @Nonnull
    private double balance;

    @Positive
    @Min(0)
    private double withdrawLimit;

    @NotNull
    private int activeFlag;

    @NotNull
    private String accountType;

    @Temporal(TemporalType.DATE)
    private java.sql.Date dateCreated;

    public Account() {}

    public Account(Person person, double balance, double withdrawLimit, int activeFlag,
            String accountType, java.sql.Date dateCreated) {
        this.person = person;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
        this.activeFlag = activeFlag;
        this.accountType = accountType;
        this.dateCreated = dateCreated;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public int getActive() {
        return this.activeFlag;
    }

    public void setActiveFlag(int activeFlag) {
        this.activeFlag = activeFlag;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public java.sql.Date getDateCreated() {
        return dateCreated;
    }
}
