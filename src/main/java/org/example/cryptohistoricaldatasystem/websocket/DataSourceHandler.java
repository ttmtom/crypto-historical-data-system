package org.example.cryptohistoricaldatasystem.websocket;

import org.example.cryptohistoricaldatasystem.config.WebSocketSourceProperties;
import org.example.cryptohistoricaldatasystem.service.HistoricalRecordService;
import org.example.cryptohistoricaldatasystem.websocket.source.WebSocketSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.List;

public class DataSourceHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceHandler.class);

    private final WebSocketSource source;
    private final HistoricalRecordService historicalRecordService;
    private final List<String> streams;
    private final WebSocketSourceProperties.Source sourceConfig;
    private final WebSocketConnectionManager connectionManager;

    public DataSourceHandler(
            WebSocketSource source,
            HistoricalRecordService historicalRecordService,
            WebSocketSourceProperties.Source sourceConfig,
            WebSocketConnectionManager connectionManager
    ) {
        this.source = source;
        this.historicalRecordService = historicalRecordService;
        this.streams = sourceConfig.getStreams();
        this.sourceConfig = sourceConfig;
        this.connectionManager = connectionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection established with {} ({})", source.getSourceName(), session.getId());
        source.getSubscriptionMessage(streams).ifPresent(message -> {
            logger.info("Sending subscription message to {}: {}", source.getSourceName(), message);
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                logger.error("Failed to send subscription message for {}", source.getSourceName(), e);
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        source.parseMessage(message.getPayload()).ifPresent(record -> {
            historicalRecordService.saveHistoricalRecords(Collections.singletonList(record));
            logger.debug("Saved historical record for {} - {}", record.getSymbol(), record.getTimestamp());
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.warn("Connection closed for source {}: {} - {}.", source.getSourceName(), status.getCode(), status.getReason());
        connectionManager.handleDisconnection(source, sourceConfig);
    }
}