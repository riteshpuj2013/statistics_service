package com.n26.statistics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.statistics.model.Statistics;
import com.n26.statistics.model.Transaction;
import com.n26.statistics.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    StatisticsService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void givenTransactionWithin60Seconds_whenInsertTransaction_ReturnStatus200() throws Exception {
        Transaction transaction = new Transaction(10d, System.currentTimeMillis());
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(json(transaction)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenTransactionOlderThan60Seconds_whenInsertTransaction_ReturnStatus204() throws Exception {
        Transaction transaction = new Transaction(10d, System.currentTimeMillis() - 60000);
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(json(transaction)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenGetStatistics_ReturnStatistics() throws Exception {
        Statistics statistics = new Statistics(1000, 500, 100, 1000, 2);
        Mockito.when(service.getStatistics()).thenReturn(statistics);
        mockMvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("sum", is(1000.0)))
                .andExpect(jsonPath("avg", is(500.0)))
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