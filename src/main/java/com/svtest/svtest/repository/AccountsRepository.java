package com.svtest.svtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.svtest.svtest.model.Account;

public interface AccountsRepository extends JpaRepository<Account, Long> {
     @Query(value = "UPDATE accounts a" +
        " SET a.balance = :#{#balance} " +
        " WHERE a.id  = :#{#id}", nativeQuery = true)
    void updateBalance(@Param("id") Long id, @Param("balance") double newBalance);
}
