package com.svtest.svtest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.svtest.svtest.model.Account;
import com.svtest.svtest.model.Person;
import com.svtest.svtest.service.AccountsService;
import com.svtest.svtest.service.PeopleService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.svtest.svtest.dto.*;
import com.svtest.svtest.exceptions.HttpCustomException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/account")
public class AccountsController {
    
    @Autowired
    private PeopleService peopleService;

    @Autowired
    private AccountsService accountService;

    @PostMapping("")
    public ResponseEntity<Object> createAccount(@RequestBody CreateAccountDTO account) {
        Optional<Person> accountOwner = this.peopleService.getById(account.getUserId());
        if (accountOwner.isPresent()) {
            Account newAccount = this.accountService.create(account, accountOwner.get());
            return ResponseEntity.ok(newAccount.getId());
        } else {
            return ResponseEntity.badRequest().body("Mentioned user not found!");
        }
    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<Object> getBalance(@PathVariable Long id) {
        Optional<Account> account = this.accountService.getAccountById(id);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get().getBalance());
        } else {
            return ResponseEntity.badRequest().body("mentioned account not found!");
        }
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<Object> blockAccount(@PathVariable Long id) {
        try {
            this.accountService.blockUnblockAccount(id, 0);
            return ResponseEntity.ok("Operation succesfull.");
        } catch(HttpCustomException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }

    @PutMapping("/unblock/{id}")
    public ResponseEntity<Object> unblockAccount(@PathVariable Long id) {
        try {
            this.accountService.blockUnblockAccount(id, 1);
            return ResponseEntity.ok("Operation succesfull.");
        } catch(HttpCustomException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
    
}
