CREATE TABLE historical_records
(
    timestamp       TIMESTAMP WITH TIME ZONE    NOT NULL,
    source          TEXT                        NOT NULL,
    symbol          TEXT                        NOT NULL,
    interval        TEXT                        NOT NULL,
    open_timestamp  TIMESTAMP WITH TIME ZONE    NOT NULL,
    close_timestamp TIMESTAMP WITH TIME ZONE    NOT NULL,
    open_price      DECIMAL                     NOT NULL,
    high_price      DECIMAL                     NOT NULL,
    low_price       DECIMAL                     NOT NULL,
    close_price     DECIMAL                     NOT NULL,
    volume          DECIMAL                     NOT NULL,
    source_data     JSONB                       NOT NULL,
    CONSTRAINT pk_historical_records PRIMARY KEY (timestamp, source, symbol, close_timestamp)
);

SELECT create_hypertable('historical_records', by_range('close_timestamp'), if_not_exists => TRUE);

CREATE INDEX ON historical_records (source, symbol);
CREATE INDEX ON historical_records (source, symbol, close_timestamp);
