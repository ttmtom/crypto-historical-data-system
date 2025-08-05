package org.example.cryptohistoricaldatasystem.service.ingestion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({
        "openTime",
        "open",
        "high",
        "low",
        "close",
        "volume",
        "closeTime",
        "quoteAssetVolume",
        "numberOfTrades",
        "takerBuyBaseAssetVolume",
        "takerBuyQuoteAssetVolume",
        "ignore"
})
public record Kline(
        long openTime,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        BigDecimal volume,
        long closeTime,
        BigDecimal quoteAssetVolume,
        int numberOfTrades,
        BigDecimal takerBuyBaseAssetVolume,
        BigDecimal takerBuyQuoteAssetVolume,
        BigDecimal ignore
) {
    public LocalDateTime getOpenTimeAsLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(openTime), ZoneOffset.UTC);
    }

    public LocalDateTime getCloseTimeAsLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(closeTime), ZoneOffset.UTC);
    }
}
