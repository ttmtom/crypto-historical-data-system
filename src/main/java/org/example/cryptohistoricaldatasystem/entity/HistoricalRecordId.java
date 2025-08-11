package org.example.cryptohistoricaldatasystem.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class HistoricalRecordId implements Serializable {

    private Instant timestamp;
    private String source;
    private String symbol;

    public HistoricalRecordId() {
    }

    public HistoricalRecordId(Instant timestamp, String symbol, String source) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.source = source;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalRecordId that = (HistoricalRecordId) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(symbol, that.symbol) && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, source, symbol);
    }
}
