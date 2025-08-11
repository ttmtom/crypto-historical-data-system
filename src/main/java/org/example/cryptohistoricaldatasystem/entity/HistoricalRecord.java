package org.example.cryptohistoricaldatasystem.entity;

import com.fasterxml.jackson.databind.JsonNode;
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
    private String source;

    @Id
    private String symbol;

    public Instant getCloseTimestamp() {
        return closeTimestamp;
    }

    public void setCloseTimestamp(Instant closeTimestamp) {
        this.closeTimestamp = closeTimestamp;
    }

    public Instant getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(Instant openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    @Column(name = "close_timestamp", nullable = false)
    private Instant closeTimestamp;

    @Column(name = "open_timestamp", nullable = false)
    private Instant openTimestamp;

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

    @Column(name = "interval", nullable = false)
    private String interval;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> sourceData;

    public HistoricalRecord() {}

    public HistoricalRecord(Instant eventAt, String sourceName, JsonNode klineNode, Map<String, Object> sourceData) {
        this.setTimestamp(eventAt);
        this.setSymbol(klineNode.get("s").asText());
        this.setSource(sourceName);
        this.setOpenTimestamp(Instant.ofEpochMilli(klineNode.get("t").asLong()));
        this.setCloseTimestamp(Instant.ofEpochMilli(klineNode.get("T").asLong()));
        this.setInterval(klineNode.get("i").asText());
        this.setOpen(new BigDecimal(klineNode.get("o").asText()));
        this.setHigh(new BigDecimal(klineNode.get("h").asText()));
        this.setLow(new BigDecimal(klineNode.get("l").asText()));
        this.setClose(new BigDecimal(klineNode.get("c").asText()));
        this.setVolume(new BigDecimal(klineNode.get("v").asText()));
        this.setSourceData(sourceData);
    }

    // Getters and setters
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
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

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Map<String, Object> getSourceData() {
        return sourceData;
    }

    public void setSourceData(Map<String, Object> sourceData) {
        this.sourceData = sourceData;
    }
}
