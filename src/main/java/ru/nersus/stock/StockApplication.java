package ru.nersus.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockApplication {

    Logger log = LoggerFactory.getLogger("MAIN");

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

}
