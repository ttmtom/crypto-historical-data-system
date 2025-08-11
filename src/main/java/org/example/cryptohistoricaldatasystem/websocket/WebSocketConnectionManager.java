package org.example.cryptohistoricaldatasystem.websocket;

import jakarta.annotation.PostConstruct;
import org.example.cryptohistoricaldatasystem.config.WebSocketSourceProperties;
import org.example.cryptohistoricaldatasystem.service.HistoricalRecordService;
import org.example.cryptohistoricaldatasystem.websocket.source.WebSocketSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class WebSocketConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConnectionManager.class);

    private final WebSocketSourceProperties properties;
    private final HistoricalRecordService historicalRecordService;
    private final Map<String, WebSocketSource> sources;
    private final ScheduledExecutorService reconnectExecutor;
    private final Map<String, Long> reconnectDelays = new ConcurrentHashMap<>();

    public WebSocketConnectionManager(
        WebSocketSourceProperties properties,
        HistoricalRecordService historicalRecordService,
        Map<String, WebSocketSource> sources,
        ScheduledExecutorService reconnectExecutor
    ) {
        this.properties = properties;
        this.historicalRecordService = historicalRecordService;
        this.sources = sources;
        this.reconnectExecutor = reconnectExecutor;
    }

    @PostConstruct
    public void connectToSources() {
        properties.getSources().forEach((name, sourceConfig) -> {
            if (sourceConfig.isEnabled()) {
                WebSocketSource source = sources.get(name);
                if (source != null) {
                    logger.info("Initializing WebSocket connection for source: {}", name);
                    connect(source, sourceConfig);
                } else {
                    logger.warn("No WebSocketSource bean found for configured source: {}", name);
                }
            }
        });
    }

    private void connect(WebSocketSource source, WebSocketSourceProperties.Source sourceConfig) {
        WebSocketClient client = new StandardWebSocketClient();
        DataSourceHandler handler = new DataSourceHandler(source, historicalRecordService, sourceConfig, this);
        String finalUrl = source.buildUrl(sourceConfig.getUrl(), sourceConfig.getStreams());
        logger.info("Attempting to connect to {} with URL: {}", source.getSourceName(), finalUrl);

        CompletableFuture<WebSocketSession> future = client.execute(handler, finalUrl);

        future.whenComplete((session, ex) -> {
            if (ex != null) {
                // This is called if the connection attempt itself fails.
                logger.error("Failed to connect to source: {}. Error: {}", source.getSourceName(), ex.getMessage());
                handleDisconnection(source, sourceConfig);
            } else {
                // This is called when the connection is established successfully.
                logger.info("Successfully connected to source: {}. Session ID: {}", source.getSourceName(), session.getId());
                // Reset the reconnect delay upon a successful connection.
                reconnectDelays.put(source.getSourceName(), sourceConfig.getReconnectInitialDelay());
            }
        });
    }

    public void handleDisconnection(WebSocketSource source, WebSocketSourceProperties.Source sourceConfig) {
        if (!sourceConfig.isAutoReconnect()) {
            logger.info("Auto-reconnect is disabled for source: {}.", source.getSourceName());
            return;
        }

        long currentDelay = reconnectDelays.getOrDefault(source.getSourceName(), sourceConfig.getReconnectInitialDelay());
        logger.warn("Connection lost for {}. Scheduling reconnect attempt in {} ms.", source.getSourceName(), currentDelay);

        reconnectExecutor.schedule(() -> {
            logger.info("Executing scheduled reconnect for source: {}", source.getSourceName());
            connect(source, sourceConfig);
        }, currentDelay, TimeUnit.MILLISECONDS);

        // Calculate the next delay using exponential backoff
        long nextDelay = (long) (currentDelay * sourceConfig.getReconnectMultiplier());
        reconnectDelays.put(source.getSourceName(), Math.min(nextDelay, sourceConfig.getReconnectMaxDelay()));
    }
}