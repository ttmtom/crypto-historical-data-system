package org.example.cryptohistoricaldatasystem.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "historical_records")
@IdClass(HistoricalRecordId.class)
public class HistoricalRecord {

    @Id
    private Instant timestamp;

    @Id
    private String symbol;

    @Column(name = "open_price", nullable = false)
    private BigDecimal open;

    @Column(name = "high_price", nullable = false)
    private BigDecimal high;

    @Column(name = "low_price", nullable = false)
    private BigDecimal low;

    @Column(name = "close_price", nullable = false)
    private BigDecimal close;

    @Column(nullable = false)
    private BigDecimal volume;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> sourceData;

    // Getters and setters
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

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Map<String, Object> getSourceData() {
        return sourceData;
    }

    public void setSourceData(Map<String, Object> sourceData) {
        this.sourceData = sourceData;
    }
}
