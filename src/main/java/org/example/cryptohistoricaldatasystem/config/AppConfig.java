package org.example.cryptohistoricaldatasystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class AppConfig {

    @Bean
    public ScheduledExecutorService webSocketReconnectExecutor() {
        // Using a daemon thread ensures it doesn't prevent the application from shutting down.
        return Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "websocket-reconnect-scheduler");
            t.setDaemon(true);
            return t;
        });
    }
}