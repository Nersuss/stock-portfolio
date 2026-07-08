package ru.nersus.stock.calculation;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

@Disabled
class TechIndicatorsRawTest {

    @Test
    void rsi() {
        double rsiRes = TechIndicatorsRaw.rsi(List.of(90., 91., 92., 93., 94., 95., 96., 97., 98., 99., 100., 101., 102., 103., 104., 10.));
        System.out.println(rsiRes);
    }

    @Test
    void stochastic() {
        double stochasticRes = TechIndicatorsRaw.stochastic(
                List.of(90., 91., 92.),
                List.of(96., 97., 98.),
                95);
        System.out.println(stochasticRes);
    }

}
