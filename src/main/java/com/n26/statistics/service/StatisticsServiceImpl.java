package com.n26.statistics.service;

import com.n26.statistics.cache.StatisticsCache;
import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsCache statisticsCache;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction insertTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Statistics getStatistics() {
        return statisticsCache.getStatistics();
    }

}
