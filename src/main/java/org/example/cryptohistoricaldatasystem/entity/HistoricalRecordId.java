package org.example.cryptohistoricaldatasystem.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class HistoricalRecordId implements Serializable {

    private Instant timestamp;
    private String symbol;

    public HistoricalRecordId() {
    }

    public HistoricalRecordId(Instant timestamp, String symbol) {
        this.timestamp = timestamp;
        this.symbol = symbol;
    }

    // Getters, setters, equals, and hashCode
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalRecordId that = (HistoricalRecordId) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, symbol);
    }
}
