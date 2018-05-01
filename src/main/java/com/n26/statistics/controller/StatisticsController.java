package com.n26.statistics.controller;

import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@RestController
public class StatisticsController {

    @Autowired
    StatisticsService service;

    /**
     * Inserts Transaction in database and returns status 200 if transaction timestamp within 60 seconds
     * else returns 204 status
     * @param transaction
     * @return
     */
    @PostMapping("/transactions")
    public ResponseEntity insertTransaction(@RequestBody @Valid Transaction transaction) {
        service.insertTransaction(transaction);
        long currentTimeInMilliseconds = Instant.now().minusSeconds(60).toEpochMilli();
        return transaction.getTimestamp() < currentTimeInMilliseconds ? createResponse(HttpStatus.NO_CONTENT) : createResponse(HttpStatus.OK);
    }

    /**
     * Returns RealTime statistics of the transaction executed in last 60 seconds
     * @return
     */
    @GetMapping("/statistics")
    public Statistics getStatistics() {
        return service.getStatistics();
    }

    private ResponseEntity createResponse(HttpStatus status) {
        return ResponseEntity.status(status).build();
    }


}
