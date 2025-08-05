CREATE TABLE IF NOT EXISTS golden_records
(
    timestamp   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    symbol      TEXT                        NOT NULL,
    open_price  NUMERIC(21, 8)              NOT NULL,
    high_price  NUMERIC(21, 8)              NOT NULL,
    low_price   NUMERIC(21, 8)              NOT NULL,
    close_price NUMERIC(21, 8)              NOT NULL,
    volume      NUMERIC(21, 8)              NOT NULL,
    source_data JSONB                       NOT NULL,
    PRIMARY KEY (timestamp, symbol)
);

SELECT create_hypertable('golden_records', by_range('timestamp'), if_not_exists => TRUE);
