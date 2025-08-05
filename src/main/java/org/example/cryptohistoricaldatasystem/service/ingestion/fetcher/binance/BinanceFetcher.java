package org.example.cryptohistoricaldatasystem.service.ingestion.fetcher.binance;

import org.example.cryptohistoricaldatasystem.service.ingestion.fetcher.AbstractFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

@Service
public class BinanceFetcher extends AbstractFetcher {

    public BinanceFetcher(WebClient.Builder webClientBuilder, @Value("${binance.api.url}") String binanceApiUrl) {
        super(webClientBuilder, binanceApiUrl, "Binance");
    }

    @Override
    protected Function<UriBuilder, URI> getUriFunction() {
        return uriBuilder -> uriBuilder
                .path("/api/v3/klines")
                .queryParam("symbol", "BTCUSDT")
                .queryParam("interval", "1m")
                .queryParam("limit", "1")
                .build();
    }
}
