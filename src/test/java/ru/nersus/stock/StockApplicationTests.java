package ru.nersus.stock;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import ru.nersus.stock.dto.api.StockPricesDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class StockApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void contextLoads() {

    }

    @Test
    void jsonToStockPricesDto() {
        String json = """
                {
                  "Meta Data": {
                    "1. Information": "Weekly Prices (open, high, low, close) and Volumes",
                    "2. Symbol": "AAPL",
                    "3. Last Refreshed": "2025-09-09",
                    "4. Time Zone": "US/Eastern"
                  },
                  "Weekly Time Series": {
                    "2025-09-09": {
                      "1. open": "239.3000",
                      "2. high": "240.1500",
                      "3. low": "233.3600",
                      "4. close": "234.3500",
                      "5. volume": "115313413"
                    },
                    "2025-09-05": {
                      "1. open": "229.2500",
                      "2. high": "241.3200",
                      "3. low": "226.9700",
                      "4. close": "239.6900",
                      "5. volume": "212557180"
                    }
                  }
                }
                """;
        StockPricesDto stockPricesDto = new Gson().fromJson(json, StockPricesDto.class);
        assertNotNull(stockPricesDto);
    }



}
