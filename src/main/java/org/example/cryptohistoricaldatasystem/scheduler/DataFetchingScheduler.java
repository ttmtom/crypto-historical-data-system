package org.example.cryptohistoricaldatasystem.scheduler;

import org.example.cryptohistoricaldatasystem.service.ingestion.fetcher.Fetcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class DataFetchingScheduler {

    private final List<Fetcher> fetchers;

    public DataFetchingScheduler(List<Fetcher> fetchers) {
        this.fetchers = fetchers;
    }

    @Scheduled(cron = "0 * * * * ?") // Every minute
    public void fetchData() {
        Flux.merge(fetchers.stream().map(Fetcher::fetch).toList()).subscribe();
    }
}
