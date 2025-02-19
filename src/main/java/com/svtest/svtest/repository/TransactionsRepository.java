package com.svtest.svtest.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.svtest.svtest.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM transactions t"
        + " WHERE t.account_id  = :#{#id}", nativeQuery = true)
    List<Transaction> getAllTransactionsByAccount(@Param("id") Long id);

    @Query(value = "SELECT * FROM transactions t WHERE " + 
        "t.transaction_date >= TO_DATE(:#{#startPeriod}, 'yyyy/mm/dd') AND " + 
        "t.transaction_date <= TO_DATE(:#{#endPeriod}, 'yyyy/mm/dd') AND " +
        " t.account_id  = :#{#id}", nativeQuery = true)
    List<Transaction> getAllPeriodTransactionsByAccount(@Param("id") Long id,
        @Param("startPeriod") String startPeriod, @Param("endPeriod") String endPeriod);
}
