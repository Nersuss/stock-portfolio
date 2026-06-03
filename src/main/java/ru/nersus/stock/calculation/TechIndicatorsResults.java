package ru.nersus.stock.calculation;

import ru.nersus.stock.dto.IndicatorValue;
import ru.nersus.stock.dto.TechIndicatorsDto;
import ru.nersus.stock.dto.api.Candle;

import java.util.List;

public class TechIndicatorsResults {

    public static TechIndicatorsDto getIndicators(List<Candle> prices) {

        List<Double> openPrices = prices.stream().map(Candle::open).toList();
        List<Double> lowPrices = prices.stream().map(Candle::low).toList();
        List<Double> highPrices = prices.stream().map(Candle::high).toList();
        Double close = prices.getLast().close();

        return new TechIndicatorsDto(
                rsi(openPrices),
                stochastic(lowPrices, highPrices, close)
        );
    }

    public static IndicatorValue rsi(List<Double> prices) {
        double resRaw = TechIndicatorsRaw.rsi(prices);

        if (resRaw < 0 || resRaw > 100) {
            throw new ArithmeticException("Invalid RSI result");
        }

        if (resRaw >= 70) {
            return new IndicatorValue(IndicatorPredict.SELL, resRaw);
        }
        if (resRaw <= 30) {
            return new IndicatorValue(IndicatorPredict.BUY, resRaw);
        }
        return new IndicatorValue(IndicatorPredict.NEUTRAL, resRaw);
    }

    public static IndicatorValue stochastic(List<Double> low, List<Double> high, double close) {//Стохастический осциллятор
        double resRaw = TechIndicatorsRaw.stochastic(low, high, close);

        if (resRaw < 0 || resRaw > 100) {
            throw new ArithmeticException("Invalid stochastic result");
        }

        if (resRaw >= 80) {
            return new IndicatorValue(IndicatorPredict.SELL, resRaw);
        }
        if (resRaw <= 20) {
            return new IndicatorValue(IndicatorPredict.BUY, resRaw);
        }
        return new IndicatorValue(IndicatorPredict.NEUTRAL, resRaw);
    }

}
