package com.n26.statistics.model;

import java.util.Objects;

public class Statistics {

    private double sum;

    private double avg;

    private double min;

    private double max;

    private long count;

    public Statistics() {
    }

    public Statistics(double sum, double avg, double min, double max, long count) {

        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Double.compare(that.sum, sum) == 0 &&
                Double.compare(that.avg, avg) == 0 &&
                Double.compare(that.min, min) == 0 &&
                Double.compare(that.max, max) == 0 &&
                count == that.count;
    }

    @Override
    public int hashCode() {

        return Objects.hash(sum, avg, min, max, count);
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", min=" + min +
                ", max=" + max +
                ", count=" + count +
                '}';
    }
}
