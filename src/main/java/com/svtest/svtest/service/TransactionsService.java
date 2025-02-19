package com.svtest.svtest.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svtest.svtest.exceptions.HttpCustomException;
import com.svtest.svtest.model.Account;
import com.svtest.svtest.model.Transaction;
import com.svtest.svtest.repository.TransactionsRepository;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionRepository;

    @Autowired
    private AccountsService accountService;

    public Transaction createTransaction(Account account, double value) {
        LocalDate locald = LocalDate.now();
        Date date = Date.valueOf(locald);

        Transaction newTransaction = new Transaction(account, value, date);
        return this.transactionRepository.save(newTransaction);
    }

    public List<Transaction> getAllByAccount(long accountId) {
        return this.transactionRepository.getAllTransactionsByAccount(accountId);
    }

    public List<Transaction> getBankStatementByPeriods(Long accountId,
            String formatedDateStart, String formatedDateEnd) throws HttpCustomException {
        Optional<Account> account = this.accountService.getAccountById(accountId);
        if (account.isPresent()) {
            return this.transactionRepository.getAllPeriodTransactionsByAccount(accountId,
                formatedDateStart, formatedDateEnd);
        } else {
            throw new HttpCustomException("The mentioned account does not exists!");
        }
        
    }

    public void makeDeposit(long accountId, double value) throws HttpCustomException {
        Optional<Account> account = this.accountService.getAccountById(accountId);
        if (account.isPresent()) {
            Account referedAccount = account.get();
            double newBalance = referedAccount.getBalance() + value;
            this.createTransaction(referedAccount, value);
            referedAccount.setBalance(newBalance);
            this.accountService.update(referedAccount);
        } else {
            throw new HttpCustomException("The mentioned account does not exists!");
        }
    }

    public void makeWithdraw(long accountId, double value) throws HttpCustomException {
        Optional<Account> account = this.accountService.getAccountById(accountId);
        if (account.isPresent()) {
            Account referedAccount = account.get();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String formatedDate = LocalDate.now().format(formatter);

            List<Transaction> transactions = this.getBankStatementByPeriods(
                accountId , formatedDate, formatedDate);
            double sum = 0.0;
            for (int i = 0; i < transactions.size(); i++) {
                if (transactions.get(i).getValue() < 0) {
                    sum = sum + transactions.get(i).getValue();
                }
            }

            sum = sum * -1;
            double newBalance = referedAccount.getBalance() - value;

            if (sum + value > referedAccount.getWithdrawLimit()) {
                throw new HttpCustomException("Your withdraw limit wont allow this opperation!");
            }

            if (newBalance < 0) {
                throw new HttpCustomException("Not enough funds!");
            }

            this.createTransaction(referedAccount, value * -1);
            this.accountService.update(referedAccount);
        } else {
            throw new HttpCustomException("mentioned account not found!");
        }
    }
}
