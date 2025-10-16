package ru.nersus.stock.calculation;

import ru.nersus.stock.dto.TechIndicatorsDto;

import java.util.List;

public class TechIndicatorsResults {

    public static TechIndicatorsDto getIndicators(List<Double> prices) {



//        new TechIndicatorsDto(
//                rsi(prices),
//                stochastic()
//        );
        return null;
    }

    public static IndicatorValue rsi(List<Double> prices) {
        double resRaw = TechIndicatorsRaw.rsi(prices);

        if (resRaw < 0 || resRaw > 100) {
            throw new ArithmeticException("Invalid RSI result");
        }

        if (resRaw >= 70) {
            return IndicatorValue.SELL;
        }
        if (resRaw <= 30) {
            return IndicatorValue.BUY;
        }
        return IndicatorValue.NEUTRAL;
    }

    public static IndicatorValue stochastic(List<Double> low, List<Double> high, double close) {//Стохастический осциллятор
        double resRaw = TechIndicatorsRaw.stochastic(low, high, close);

        if (resRaw < 0 || resRaw > 100) {
            throw new ArithmeticException("Invalid stochastic result");
        }

        if (resRaw >= 80) {
            return IndicatorValue.SELL;
        }
        if (resRaw <= 20) {
            return IndicatorValue.BUY;
        }
        return IndicatorValue.NEUTRAL;
    }



}
