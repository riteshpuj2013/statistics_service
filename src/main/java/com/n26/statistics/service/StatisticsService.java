package com.n26.statistics.service;

import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;

public interface StatisticsService {
    Transaction insertTransaction(Transaction transaction);

    Statistics getStatistics();
}
