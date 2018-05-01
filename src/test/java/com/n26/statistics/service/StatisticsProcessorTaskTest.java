package com.n26.statistics.service;

import com.n26.statistics.cache.StatisticsCache;
import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;

@RunWith(SpringRunner.class)
public class StatisticsProcessorTaskTest {

    @Autowired
    StatisticsCache statisticsCache;

    @Autowired
    StatisticsProcessorTask statisticsProcessorTask;

    @MockBean
    TransactionRepository transactionRepository;

    @Before
    public void setUp() {
        Transaction recenttransaction = new Transaction(1000d, Instant.now().toEpochMilli());
        Transaction recenttransaction1 = new Transaction(100d, Instant.now().toEpochMilli());
        BDDMockito.given(transactionRepository.findAllByTimestampAfter(ArgumentMatchers.anyLong())).willReturn(Arrays.asList(recenttransaction, recenttransaction1));
    }

    @Test
    public void givenTransactions_whenProcessStatisticsUntil_UpdateStatistics() {

        //given
        Statistics statistics = new Statistics(1100, 550, 100, 1000, 2);

        //act
        statisticsProcessorTask.processStatisticsUntil();
        Statistics statisticsFromCache = statisticsCache.getStatistics();

        //verify
        Assert.assertNotNull(statisticsFromCache);
        Assert.assertEquals(statistics.getSum(), statisticsFromCache.getSum(), 0);
        Assert.assertEquals(statistics.getMax(), statisticsFromCache.getMax(), 0);
        Assert.assertEquals(statistics.getMin(), statisticsFromCache.getMin(), 0);
        Assert.assertEquals(statistics.getAvg(), statisticsFromCache.getAvg(), 0);
        Assert.assertEquals(statistics.getCount(), statisticsFromCache.getCount());
    }

    @TestConfiguration
    static class StatisticsProcessorTaskTestConfiguration {
        @Bean
        public StatisticsProcessorTask statisticsProcessorTask() {
            return new StatisticsProcessorTask();
        }

        @Bean
        public StatisticsCache statisticsCache() {
            return new StatisticsCache();
        }
    }
}