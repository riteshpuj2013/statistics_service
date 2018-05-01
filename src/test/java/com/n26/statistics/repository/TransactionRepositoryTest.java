package com.n26.statistics.repository;

import com.n26.statistics.model.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void givenTransaction_whenSave_InsertionTransactionInDB() {
        //given
        Transaction transaction = new Transaction(1000d, Instant.now().toEpochMilli());

        //act
        Transaction insertedTransaction = transactionRepository.save(transaction);
        System.out.println(insertedTransaction);

        //verify
        Assert.assertNotNull(insertedTransaction);
        Assert.assertEquals(transaction.getTimestamp(), insertedTransaction.getTimestamp(), 0);
        Assert.assertEquals(transaction.getAmount(), insertedTransaction.getAmount(), 0);
        Assert.assertTrue(insertedTransaction.getId() > 0);
    }

    @Test
    public void givenTransactions_whenFindByAllTimestampAfter_ReturnTransactionsWithinLast60Seconds() {
        //given
        Transaction recenttransaction = new Transaction(1000d, Instant.now().toEpochMilli());
        Transaction oldtransaction = new Transaction(1000d, Instant.now().toEpochMilli() - 70000);


        //act
        transactionRepository.saveAll(Arrays.asList(recenttransaction, oldtransaction));
        List<Transaction> allByTimestampAfter = transactionRepository.findAllByTimestampAfter(Instant.now().toEpochMilli() - 60000);
        Transaction transaction = allByTimestampAfter.iterator().next();
        long totalTransactions = allByTimestampAfter.parallelStream().count();

        //verify
        Assert.assertEquals(transaction.getTimestamp(), recenttransaction.getTimestamp(), 0);
        Assert.assertEquals(transaction.getAmount(), recenttransaction.getAmount(), 0);
        Assert.assertTrue(transaction.getId() > 0);
        Assert.assertEquals(totalTransactions, 1);
    }
}