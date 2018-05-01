package com.n26.statistics.service;

import com.n26.statistics.cache.StatisticsCache;
import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Component
public class StatisticsProcessorTask {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StatisticsCache statisticsCache;

    private long minuteInMillis = 60000;

    /**
     * Runs scheduled task every 500 milliseconds such that statistics are updated in StatisticsCache
     * for all transactions executed in last 60 seconds
     */
    @Scheduled(fixedRate = 500)
    public void processStatisticsUntil() {
        List<Transaction> allByTimestampAfter = transactionRepository.findAllByTimestampAfter(Instant.now().toEpochMilli() - minuteInMillis);
        Statistics statistics = statisticsCache.getStatistics();
        DoubleSummaryStatistics amountSummaryStatistics = allByTimestampAfter.stream().mapToDouble(Transaction::getAmount).summaryStatistics();
        statistics.setAvg(amountSummaryStatistics.getAverage());
        statistics.setCount(amountSummaryStatistics.getCount());
        statistics.setMax(amountSummaryStatistics.getMax());
        statistics.setMin(amountSummaryStatistics.getMin());
        statistics.setSum(amountSummaryStatistics.getSum());
        log.debug("stats {} ", statistics);
    }

    public long getMinuteInMillis() {
        return minuteInMillis;
    }

    public void setMinuteInMillis(long minuteInMillis) {
        this.minuteInMillis = minuteInMillis;
    }
}
