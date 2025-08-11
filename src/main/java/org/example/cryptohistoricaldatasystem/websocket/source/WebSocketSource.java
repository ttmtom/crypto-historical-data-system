package org.example.cryptohistoricaldatasystem.websocket.source;

import org.example.cryptohistoricaldatasystem.entity.HistoricalRecord;

import java.util.List;
import java.util.Optional;

public interface WebSocketSource {
    String getSourceName();
    Optional<HistoricalRecord> parseMessage(String message);
    Optional<String> getSubscriptionMessage(List<String> streams);

    /**
     * Builds the final WebSocket URL. The default implementation simply returns the base URL.
     * Implementations can override this to construct a more complex URL, for example, by embedding stream names.
     *
     * @param baseUrl The base URL from configuration.
     * @param streams The list of streams to subscribe to.
     * @return The final connection URL.
     */
    default String buildUrl(String baseUrl, List<String> streams) {
        return baseUrl;
    }
}