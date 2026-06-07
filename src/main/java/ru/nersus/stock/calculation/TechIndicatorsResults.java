package ru.nersus.stock.calculation;

import ru.nersus.stock.dto.IndicatorValue;
import ru.nersus.stock.dto.TechIndicatorsDto;
import ru.nersus.stock.dto.api.Candle;

import java.util.List;

public class TechIndicatorsResults {

    public static TechIndicatorsDto getIndicators(List<Candle> prices) {
        if (!prices.isEmpty()){
            List<Double> openPrices = prices.stream().map(Candle::open).toList();
            List<Double> closePrices = prices.stream().map(Candle::close).toList();
            List<Double> lowPrices = prices.stream().map(Candle::low).toList();
            List<Double> highPrices = prices.stream().map(Candle::high).toList();
            Double close = prices.getLast().close();
            Double oldClose = prices.getFirst().close();

            return new TechIndicatorsDto(
                    stochastic(lowPrices, highPrices, close),
                    rsi(openPrices),
                    ema(closePrices),
                    sma(closePrices),
                    momentum(close, oldClose),
                    new IndicatorValue(IndicatorPredict.NEUTRAL, 12.2)
            );
        }
        return null;
    }

    public static IndicatorValue rsi(List<Double> prices) {
        double resRaw = TechIndicatorsRaw.rsi(prices);

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

        if (resRaw >= 80) {
            return new IndicatorValue(IndicatorPredict.SELL, resRaw);
        }
        if (resRaw <= 20) {
            return new IndicatorValue(IndicatorPredict.BUY, resRaw);
        }
        return new IndicatorValue(IndicatorPredict.NEUTRAL, resRaw);
    }

    public static IndicatorValue momentum(double close, double oldClose) {//momentum
        double resRaw = TechIndicatorsRaw.momentum(close, oldClose);

        if (resRaw >= 80) {
            return new IndicatorValue(IndicatorPredict.SELL, resRaw);
        }
        if (resRaw <= 20) {
            return new IndicatorValue(IndicatorPredict.BUY, resRaw);
        }
        return new IndicatorValue(IndicatorPredict.NEUTRAL, resRaw);
    }

    public static IndicatorValue sma(List<Double> closePrices) {//sma
        double resRaw = TechIndicatorsRaw.sma(closePrices);

        if (resRaw >= 80) {
            return new IndicatorValue(IndicatorPredict.SELL, resRaw);
        }
        if (resRaw <= 20) {
            return new IndicatorValue(IndicatorPredict.BUY, resRaw);
        }
        return new IndicatorValue(IndicatorPredict.NEUTRAL, resRaw);
    }

    public static IndicatorValue ema(List<Double> closePrices) {//ema
        double resRaw = TechIndicatorsRaw.ema(closePrices);

        if (resRaw >= 80) {
            return new IndicatorValue(IndicatorPredict.SELL, resRaw);
        }
        if (resRaw <= 20) {
            return new IndicatorValue(IndicatorPredict.BUY, resRaw);
        }
        return new IndicatorValue(IndicatorPredict.NEUTRAL, resRaw);
    }

}
