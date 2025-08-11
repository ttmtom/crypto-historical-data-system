package org.example.cryptohistoricaldatasystem.service.ingestion.fetcher;

public enum Coin {
    BTCUSDT("BTCUSDT"),
    ETHUSDT("ETHUSDT");

    private final String symbol;

    Coin(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
