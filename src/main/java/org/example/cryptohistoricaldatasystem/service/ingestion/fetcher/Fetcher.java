package org.example.cryptohistoricaldatasystem.service.ingestion.fetcher;

import org.example.cryptohistoricaldatasystem.service.ingestion.dto.Kline;
import reactor.core.publisher.Flux;

public interface Fetcher {
    Flux<Kline> fetch();
}
