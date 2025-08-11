package org.example.cryptohistoricaldatasystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CryptoHistoricalDataSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoHistoricalDataSystemApplication.class, args);
    }

}
