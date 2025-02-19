package com.svtest.svtest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.svtest.svtest.dto.DepositWithdrawDTO;
import com.svtest.svtest.dto.StatementPeriodsDTO;
import com.svtest.svtest.exceptions.HttpCustomException;
import com.svtest.svtest.model.Account;
import com.svtest.svtest.model.Transaction;
import com.svtest.svtest.service.AccountsService;
import com.svtest.svtest.service.TransactionsService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {

    @Autowired
    private AccountsService accountsService;
    @Autowired
    private TransactionsService transactionsService;
    
    @PostMapping("/deposit")
    public ResponseEntity<Object> makeDeposit(@RequestBody DepositWithdrawDTO deposit) {
        try {
            this.transactionsService.makeDeposit(deposit.getAccountId(), deposit.getValue());
            return ResponseEntity.ok("Deposi successfull!");
        } catch(HttpCustomException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> makeWithdraw(@RequestBody DepositWithdrawDTO withdraw) {
        try {
            this.transactionsService.makeWithdraw(withdraw.getAccountId(), withdraw.getValue());
            return ResponseEntity.ok("Withdraw successfull");
        } catch(HttpCustomException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @GetMapping("/bank-statement/{accountId}")
    public ResponseEntity<Object> getBankStatement(@PathVariable long accountId) {
        Optional<Account> account = this.accountsService.getAccountById(accountId);
        if (account.isPresent()) {
            List<Transaction> transactions = this.transactionsService.getAllByAccount(accountId);
            return ResponseEntity.ok(transactions);
        } else {
            return ResponseEntity.badRequest().body("Mentioned account not found!");
        }
    }

    @GetMapping("/bank-statement-by-periods")
    public ResponseEntity<Object> getBankStatementByPeriods(@RequestBody StatementPeriodsDTO statement) {
        if (statement.getStartDate().isAfter(statement.getEndDate()) ||
                statement.getStartDate().isEqual(statement.getEndDate())) {
            return ResponseEntity.badRequest().body("Start date should be before end date");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        try {
            List<Transaction> transactions = this.transactionsService.getBankStatementByPeriods(
                statement.getAccountId() , statement.getStartDate().format(formatter),
                statement.getEndDate().format(formatter));
            return ResponseEntity.ok().body(transactions);
        } catch(HttpCustomException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
    
}
