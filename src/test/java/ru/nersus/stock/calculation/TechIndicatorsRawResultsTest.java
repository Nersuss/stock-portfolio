package ru.nersus.stock.calculation;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.nersus.stock.dto.IndicatorValue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class TechIndicatorsRawResultsTest {

    @Test
    void rsi() {
        IndicatorValue res = TechIndicatorsResults.rsi(
                List.of(90., 91., 92., 93., 94., 95., 96., 97., 98., 99., 100., 101., 102., 103., 104., 10.));
        assertEquals(new IndicatorValue(IndicatorPredict.BUY, (Double) null), res);
    }

    @Test
    void stochastic() {
    }
}