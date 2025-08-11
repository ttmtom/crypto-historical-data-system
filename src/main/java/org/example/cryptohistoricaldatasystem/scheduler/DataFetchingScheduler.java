package org.example.cryptohistoricaldatasystem.scheduler;

import org.example.cryptohistoricaldatasystem.service.ingestion.fetcher.Fetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class DataFetchingScheduler {
    private final List<Fetcher> fetchers;
    protected final Logger log = LoggerFactory.getLogger(getClass());


    public DataFetchingScheduler(List<Fetcher> fetchers) {
        this.fetchers = fetchers;
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void fetchData() {
        Flux.merge(fetchers.stream().map(Fetcher::fetch).toList())
            .subscribe(
                (data) -> {
                    this.log.info("Data fetched");
                    this.log.info(data.toString());
                },
                    (err) -> {
                        this.log.error(err.getMessage(), err);
                    },
                () -> {
                    this.log.info("Data fetching scheduled");
                });
    }
}
