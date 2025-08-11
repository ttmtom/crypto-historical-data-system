package org.example.cryptohistoricaldatasystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "crypto")
public class WebSocketSourceProperties {

    private final Map<String, Source> sources = new HashMap<>();

    public Map<String, Source> getSources() {
        return sources;
    }

    public static class Source {
        private boolean enabled;
        private String url;
        private List<String> streams;
        private boolean autoReconnect = false; // Default to false for safety
        private long reconnectInitialDelay = 2000; // 2 seconds
        private long reconnectMaxDelay = 60000; // 1 minute
        private double reconnectMultiplier = 2.0;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getStreams() { return streams; }

        public void setStreams(List<String> streams) { this.streams = streams; }

        public boolean isAutoReconnect() {
            return autoReconnect;
        }

        public void setAutoReconnect(boolean autoReconnect) {
            this.autoReconnect = autoReconnect;
        }

        public long getReconnectInitialDelay() {
            return reconnectInitialDelay;
        }

        public void setReconnectInitialDelay(long reconnectInitialDelay) {
            this.reconnectInitialDelay = reconnectInitialDelay;
        }

        public long getReconnectMaxDelay() {
            return reconnectMaxDelay;
        }

        public void setReconnectMaxDelay(long reconnectMaxDelay) {
            this.reconnectMaxDelay = reconnectMaxDelay;
        }

        public double getReconnectMultiplier() {
            return reconnectMultiplier;
        }

        public void setReconnectMultiplier(double reconnectMultiplier) {
            this.reconnectMultiplier = reconnectMultiplier;
        }
    }
}