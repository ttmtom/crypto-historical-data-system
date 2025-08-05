package org.example.cryptohistoricaldatasystem.ingestion;

import org.example.cryptohistoricaldatasystem.ingestion.dto.Kline;
import reactor.core.publisher.Flux;

public interface Fetcher {
    Flux<Kline> fetch();
}
