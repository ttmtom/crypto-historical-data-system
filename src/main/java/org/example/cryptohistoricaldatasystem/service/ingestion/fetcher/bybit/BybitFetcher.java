package org.example.cryptohistoricaldatasystem.service.ingestion.fetcher.bybit;

import org.example.cryptohistoricaldatasystem.service.ingestion.fetcher.AbstractFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

@Service
public class BybitFetcher extends AbstractFetcher {

    public BybitFetcher(WebClient.Builder webClientBuilder, @Value("${bybit.api.url}") String bybitApiUrl) {
        super(webClientBuilder, bybitApiUrl, "Bybit");
    }

    @Override
    protected Function<UriBuilder, URI> getUriFunction() {
        return uriBuilder -> uriBuilder
                .path("/v5/market/kline")
                .queryParam("category", "spot")
                .queryParam("symbol", "BTCUSDT")
                .queryParam("interval", "1")
                .queryParam("limit", "1")
                .build();
    }
}
