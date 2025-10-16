package ru.nersus.stock.calculation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TechIndicatorsRawResultsTest {

    @Test
    void rsi() {
        IndicatorValue res = TechIndicatorsResults.rsi(
                List.of(90., 91., 92., 93., 94., 95., 96., 97., 98., 99., 100., 101., 102., 103., 104., 10.));
        assertEquals(IndicatorValue.BUY, res);
    }

    @Test
    void stochastic() {
    }
}