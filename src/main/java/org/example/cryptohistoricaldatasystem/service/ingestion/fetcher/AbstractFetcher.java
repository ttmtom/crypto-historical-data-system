package org.example.cryptohistoricaldatasystem.service.ingestion.fetcher;

import org.example.cryptohistoricaldatasystem.service.ingestion.dto.Kline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.Arrays;
import java.util.function.Function;

public abstract class AbstractFetcher implements Fetcher {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final WebClient webClient;
    private final String exchangeName;

    public AbstractFetcher(WebClient.Builder webClientBuilder, String baseUrl, String exchangeName) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.exchangeName = exchangeName;
    }

    @Override
    public Flux<Kline> fetch() {
        log.info("Fetching data from {} for all symbols...", exchangeName);
        log.info(Arrays.toString(Coin.values()));
        return Flux.fromArray(Coin.values())
            .flatMap(this::fetchForSymbol);
    }

    private Flux<Kline> fetchForSymbol(Coin coin) {
        String symbol = coin.getSymbol();
        log.debug("Fetching data from {} for symbol {}...", exchangeName, symbol);
        return this.webClient.get()
            .uri(getUriFunction(symbol))
            .retrieve()
            .bodyToFlux(Kline.class)
            .doOnNext(kline -> log.info("Fetched {} kline for {}: {}", exchangeName, symbol, kline))
            .doOnError(error -> log.error("Error fetching from {} for symbol {}", exchangeName, symbol, error));
    }

    protected abstract Function<UriBuilder, URI> getUriFunction(String symbol);
}
