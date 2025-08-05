package org.example.cryptohistoricaldatasystem.controller;

import org.example.cryptohistoricaldatasystem.service.ingestion.fetcher.Fetcher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/ingestion")
public class DataIngestionController {

    private final List<Fetcher> fetchers;

    public DataIngestionController(List<Fetcher> fetcherServices) {
        this.fetchers = fetcherServices;
    }

    @PostMapping("/trigger")
    public void triggerFetch() {
        Flux.merge(fetchers.stream().map(Fetcher::fetch).toList()).subscribe();
    }
}
