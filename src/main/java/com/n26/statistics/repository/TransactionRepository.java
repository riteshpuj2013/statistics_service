package com.n26.statistics.repository;

import com.n26.statistics.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByTimestampAfter(long timestamp);
}
