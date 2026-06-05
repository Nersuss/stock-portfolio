package ru.nersus.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class StockApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public void run(String... args) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:/init.sql");
        byte[] byteArray = resource.getContentAsByteArray();
        String sql = new String(byteArray, StandardCharsets.UTF_8);
        jdbcTemplate.execute(sql);
    }
}
