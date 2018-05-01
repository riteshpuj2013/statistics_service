package com.n26.statistics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double amount;

    @Column
    private long timestamp;

    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Transaction(int id, double amount, long timestamp) {
        this.id = id;
        this.amount = amount;
        this.timestamp = timestamp;
    }


    public Transaction() {

    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                Double.compare(that.amount, amount) == 0 &&
                timestamp == that.timestamp;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, amount, timestamp);
    }
}
