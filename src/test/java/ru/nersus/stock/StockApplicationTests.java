package ru.nersus.stock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static ru.nersus.stock.calculation.TechIndicatorsRaw.calculateEMA;
import static ru.nersus.stock.calculation.TechIndicatorsRaw.calculateSMA;

@SpringBootTest
class StockApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void jsonToStockPricesDto() {
        List<Double> testPrices = List.of(
                100.0, 101.0, 102.0, 103.0, 104.0,  // старые
                105.0, 106.0, 107.0, 108.0, 109.0   // новые
        );

        Map<Integer, Double> sma = calculateSMA(testPrices, 5);
        System.out.println("SMA-5: " + sma.get(5)); // 107.0

        Map<Integer, Double> ema = calculateEMA(testPrices, 5);
        System.out.println("EMA-5: " + ema.get(5)); // 107.5
    }

}
