package com.svtest.svtest.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svtest.svtest.dto.CreateAccountDTO;
import com.svtest.svtest.exceptions.HttpCustomException;
import com.svtest.svtest.model.Account;
import com.svtest.svtest.model.Person;
import com.svtest.svtest.repository.AccountsRepository;

@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountRepository;

    public Account create(CreateAccountDTO dto, Person person) {
        LocalDate locald = LocalDate.now();
        Date date = Date.valueOf(locald);

        Account newAccount =  new Account(person, dto.getBalance(), dto.getWithdrawLimit(),
            1, dto.getAccountType(), date);

        var account = accountRepository.save(newAccount);
        return account;
    }

    public Account update(Account account) {
        return this.accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long accountId) {
        Optional<Account> account = this.accountRepository.findById(accountId);
        return account;
    }

    public boolean blockUnblockAccount(long accountId, int operation) throws HttpCustomException {
        Optional<Account> account = this.getAccountById(accountId);
        if (account.isPresent()) {
            String operationName = operation == 1 ? "active" : "blocked";
            if (account.get().getActive() == operation) {
                account.get().setActiveFlag(0);
                this.update(account.get());
                return true;
            } else {
                throw new HttpCustomException("Mentioned accout is already ".concat(operationName));
            }
        } else {
            throw new HttpCustomException("Mentioned accout not found");
        }
    }
}
