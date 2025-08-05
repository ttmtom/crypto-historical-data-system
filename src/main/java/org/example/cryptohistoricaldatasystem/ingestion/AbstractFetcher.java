package org.example.cryptohistoricaldatasystem.ingestion;

import org.example.cryptohistoricaldatasystem.ingestion.dto.Kline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
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
        log.info("Fetching data from {}...", exchangeName);
        return this.webClient.get()
                .uri(getUriFunction())
                .retrieve()
                .bodyToFlux(Kline.class)
                .doOnNext(kline -> log.info("Fetched {} kline: {}", exchangeName, kline))
                .doOnError(error -> log.error("Error fetching from {}", exchangeName, error));
    }

    protected abstract Function<UriBuilder, URI> getUriFunction();
}