package org.example.cryptohistoricaldatasystem.websocket.source;

import org.example.cryptohistoricaldatasystem.entity.HistoricalRecord;

import java.util.List;
import java.util.Optional;

public interface WebSocketSource {
    String getSourceName();
    Optional<HistoricalRecord> parseMessage(String message);
    Optional<String> getSubscriptionMessage(List<String> streams);

    default String buildUrl(String baseUrl, List<String> streams) {
        return baseUrl;
    }
}