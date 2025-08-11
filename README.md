# Crypto Historical Data System

This project is a Spring Boot application designed to collect and store cryptocurrency historical data from various WebSocket sources. It's built to be extensible, allowing for the addition of new data sources with ease. The current implementation includes a source for Binance.

## Getting Started

### Prerequisites

*   Java 21
*   Docker

### Installation

1.  **Clone the repository:**

    ```bash
    git clone <repository-url>
    cd crypto-historical-data-system
    ```

2.  **Start the database:**

    The project uses a `docker-compose.yml` file to run a TimescaleDB instance.

    ```bash
    docker-compose up -d
    ```

3.  **Run the database migrations:**

    The project uses Flyway to manage database migrations. The migrations will run automatically on application startup.

4.  **Run the application:**

    ```bash
    ./gradlew bootRun
    ```

## Configuration

The application configuration is located in `src/main/resources/application.yml`.

### Data Sources

The `crypto.sources` section configures the WebSocket data sources. Here's an example for Binance:

```yaml
crypto:
  sources:
    binance:
      enabled: true
      url: "wss://stream.binance.com:443/stream"
      streams:
        - "btcusdt@kline_1m"
        - "btcusdt@kline_5m"
        - "ethusdt@kline_1m"
        - "ethusdt@kline_5m"
        - "solusdt@kline_1m"
        - "solusdt@kline_5m"
      autoReconnect: true
      reconnectInitialDelay: 5000
      reconnectMaxDelay: 90000
      reconnectMultiplier: 2.0
```

### Database

The database connection properties are configured in `src/main/resources/application.properties` and can be overridden using environment variables. For local development, you can place these variables in a `.env` file at the root of the project.

A `.env.example` file is provided as a template. To create your local configuration file, simply copy it:

```bash
cp .env.example .env
```

The application will run without a `.env` file by using the default values specified in `application.properties`, which are suitable for the provided Docker Compose setup.

```properties
# src/main/resources/application.properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/postgres}
spring.datasource.username=${DB_USERNAME:timescaledb}
spring.datasource.password=${DB_PASSWORD:password}
```

## How it Works

The application connects to the configured WebSocket sources and listens for messages. When a message is received, it's processed and saved to the database. The `HistoricalRecord` entity represents a single data point, and the `HistoricalRecordService` handles the business logic for saving the data.

The `WebSocketConnectionManager` is responsible for managing the WebSocket connections, including reconnecting if a connection is lost.
