package com.n26.statistics.service;

import com.n26.statistics.cache.StatisticsCache;
import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class StatisticsServiceImplTest {

    @Autowired
    private StatisticsService statisticsService;

    @MockBean
    private StatisticsCache statisticsCache;

    @MockBean
    private TransactionRepository transactionRepository;

    private long timeInMillis = 1525166104578l;

    @Before
    public void setUp() {
        Statistics statistics = new Statistics(1000, 500, 100, 1000, 2);
        Mockito.when(statisticsCache.getStatistics()).thenReturn(statistics);
        Transaction transaction = new Transaction(1, 1000d, timeInMillis);
        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
    }

    @Test
    public void givenTransaction_whenInsertTransaction_thenInsert() {
        //given
        Transaction transaction = new Transaction(1000, timeInMillis);

        //act
        Transaction insertedTransaction = statisticsService.insertTransaction(transaction);
        System.out.println(insertedTransaction);

        //verify
        Assert.assertNotNull(insertedTransaction);
        Assert.assertEquals(transaction.getTimestamp(), insertedTransaction.getTimestamp(), 0);
        Assert.assertEquals(transaction.getAmount(), insertedTransaction.getAmount(), 0);
        Assert.assertTrue(insertedTransaction.getId() > 0);
    }

    @Test
    public void givenStatistics_whenGetStatistics_ReturnStatistics() {
        //given
        Statistics statistics = new Statistics(1000, 500, 100, 1000, 2);
        Mockito.when(statisticsCache.getStatistics()).thenReturn(statistics);
        //act
        Statistics statisticsFromCache = statisticsService.getStatistics();
        System.out.println(statisticsFromCache);

        //verify
        Assert.assertNotNull(statisticsFromCache);
        Assert.assertEquals(statistics.getSum(), statisticsFromCache.getSum(), 0);
        Assert.assertEquals(statistics.getMax(), statisticsFromCache.getMax(), 0);
        Assert.assertEquals(statistics.getMin(), statisticsFromCache.getMin(), 0);
        Assert.assertEquals(statistics.getAvg(), statisticsFromCache.getAvg(), 0);
        Assert.assertEquals(statistics.getCount(), statisticsFromCache.getCount());
    }

    @TestConfiguration
    static class StatisticsServiceImplTestConfiguration {
        @Bean
        public StatisticsService statisticsService() {
            return new StatisticsServiceImpl();
        }
    }
}