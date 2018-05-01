package com.n26.statistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.service.StatisticsProcessorTask;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = StatisticsServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@FixMethodOrder(MethodSorters.DEFAULT)
public class StatisticsApplicationIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StatisticsProcessorTask statisticsProcessorTask;

    @Test
    public void givenTransactions_whenInsertTransaction_ReturnStatus() throws Exception {
        Transaction recenttransaction = new Transaction(1000d, Instant.now().toEpochMilli());
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(json(recenttransaction)))
                .andExpect(status().isOk());
        Transaction recenttransaction1 = new Transaction(100d, Instant.now().toEpochMilli());
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(json(recenttransaction1)))
                .andExpect(status().isOk());
        Transaction transaction = new Transaction(10d, System.currentTimeMillis() - 60000);
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(json(transaction)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenTransactions_whenGetStatistics_ReturnStatistics() throws Exception {
        Transaction recenttransaction = new Transaction(1000d, Instant.now().toEpochMilli());
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(json(recenttransaction)))
                .andExpect(status().isOk());
        Transaction recenttransaction1 = new Transaction(100d, Instant.now().toEpochMilli());
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(json(recenttransaction1)))
                .andExpect(status().isOk());
        //For StatisticsProcessorTask to execute
        Thread.sleep(1000);
        mockMvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("sum", is(1100.0)))
                .andExpect(jsonPath("avg", is(550.0)))
                .andExpect(jsonPath("min", is(100.0)))
                .andExpect(jsonPath("max", is(1000.0)))
                .andExpect(jsonPath("count", is(2)))
                .andExpect(status().isOk());
    }

    public String json(Object obj) throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(obj);
        System.out.println(body);
        return body;
    }

}
