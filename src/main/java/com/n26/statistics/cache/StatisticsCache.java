package com.n26.statistics.cache;

import com.n26.statistics.model.Statistics;
import org.springframework.stereotype.Component;

@Component
public class StatisticsCache {
    private Statistics statistics = new Statistics();

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
