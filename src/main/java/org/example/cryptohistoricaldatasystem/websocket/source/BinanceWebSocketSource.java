package org.example.cryptohistoricaldatasystem.websocket.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cryptohistoricaldatasystem.entity.HistoricalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("binance")
public class BinanceWebSocketSource implements WebSocketSource {

    private static final Logger logger = LoggerFactory.getLogger(BinanceWebSocketSource.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getSourceName() {
        return "binance";
    }

    @Override
    public Optional<HistoricalRecord> parseMessage(String message) {
        try {
            JsonNode rootNode = objectMapper.readTree(message);
            JsonNode eventNode = rootNode;

            if (rootNode.has("stream") && rootNode.has("data")) {
                eventNode = rootNode.get("data");
            }

            if (eventNode.has("e") && "kline".equals(eventNode.get("e").asText())) {
                JsonNode klineNode = eventNode.get("k");

                if (klineNode.get("x").asBoolean()) {
                    logger.info("received a closed kline is {}", klineNode.toPrettyString());
                    HistoricalRecord record = new HistoricalRecord(
                            Instant.ofEpochMilli(eventNode.get("E").asLong()),
                            this.getSourceName(),
                            klineNode,
                            objectMapper.convertValue(rootNode, Map.class)
                    );

                    return Optional.of(record);
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("Error parsing Binance message: {}", message, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getSubscriptionMessage(List<String> streams) {
        return Optional.empty();
    }

    @Override
    public String buildUrl(String baseUrl, List<String> streams) {
        String streamNames = String.join("/", streams);

        return baseUrl + "?streams=" + streamNames;
    }
}