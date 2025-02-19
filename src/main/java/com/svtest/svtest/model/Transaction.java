package com.svtest.svtest.model;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "value")
    @NotNull
    private double value;

    @Column(name = "transaction_date")
    @Temporal(TemporalType.DATE)
    private java.sql.Date transactionDate;

    public Transaction() {}

    public Transaction(Account account, double value, java.sql.Date transactionDate) {
        this.account = account;
        this.value = value;
        this.transactionDate = transactionDate;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public java.sql.Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(java.sql.Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
