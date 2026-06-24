package ru.nersus.stock.calculation;

import ru.nersus.stock.dto.IndicatorValue;
import ru.nersus.stock.dto.TechIndicatorsDto;
import ru.nersus.stock.dto.api.Candle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TechIndicatorsResults {

    public static TechIndicatorsDto getIndicators(List<Candle> prices) {
        if (!prices.isEmpty()) {
            List<Double> openPrices = prices.stream().map(Candle::open).toList();
            List<Double> closePrices = prices.stream().map(Candle::close).toList();
            List<Double> lowPrices = prices.stream().map(Candle::low).toList();
            List<Double> highPrices = prices.stream().map(Candle::high).toList();
            Double close = prices.getLast().close();
            Double oldClose = prices.getFirst().close();

            return new TechIndicatorsDto(
                    emas(closePrices),
                    smas(closePrices),
                    stochastic(lowPrices, highPrices, close),
                    rsi(openPrices),
                    momentum(close, oldClose),
                    williams(lowPrices, highPrices, close),
                    vhf(closePrices),
                    mfi(closePrices)
            );
        }
        return null;
    }

    public static List<IndicatorValue> smas(List<Double> closePrices) {
        final int[] SMA_PERIODS = {10, 20, 30, 50, 100, 200};
        Map<Integer, Double> smaValues = TechIndicatorsRaw.calculateSMA(closePrices, SMA_PERIODS);

        double lastPrice = closePrices.getLast();
        List<IndicatorValue> results = new ArrayList<>();

        for (int period : SMA_PERIODS) {
            Double smaValue = smaValues.get(period);
            if (smaValue != null) {
                IndicatorPredict predict;
                if (lastPrice > smaValue) {
                    predict = IndicatorPredict.BUY;  // Цена выше SMA — бычий сигнал
                } else if (lastPrice < smaValue) {
                    predict = IndicatorPredict.SELL; // Цена ниже SMA — медвежий сигнал
                } else {
                    predict = IndicatorPredict.NEUTRAL;
                }
                results.add(new IndicatorValue(predict, smaValue));
            } else {
                results.add(new IndicatorValue(IndicatorPredict.NEUTRAL, null));
            }
        }

        return results;
    }

    public static List<IndicatorValue> emas(List<Double> closePrices) {
        final int[] EMA_PERIODS = {10, 20, 30, 50, 100, 200};
        Map<Integer, Double> emaValues = TechIndicatorsRaw.calculateEMA(closePrices, EMA_PERIODS);

        double lastPrice = closePrices.getLast();
        List<IndicatorValue> results = new ArrayList<>();

        for (int period : EMA_PERIODS) {
            Double emaValue = emaValues.get(period);
            if (emaValue != null) {
                IndicatorPredict predict;
                if (lastPrice > emaValue) {
                    predict = IndicatorPredict.BUY;  // Цена выше EMA — бычий сигнал
                } else if (lastPrice < emaValue) {
                    predict = IndicatorPredict.SELL; // Цена ниже EMA — медвежий сигнал
                } else {
                    predict = IndicatorPredict.NEUTRAL;
                }
                results.add(new IndicatorValue(predict, emaValue));
            } else {
                results.add(new IndicatorValue(IndicatorPredict.NEUTRAL, null));
            }
        }

        return results;
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

    public static IndicatorValue williams(List<Double> low, List<Double> high, double close) {//Уильямса процентный диапазон
        double resRaw = TechIndicatorsRaw.williams(low, high, close);

        if (resRaw >= 80) {
            return new IndicatorValue(IndicatorPredict.SELL, resRaw);
        }
        if (resRaw <= 20) {
            return new IndicatorValue(IndicatorPredict.BUY, resRaw);
        }
        return new IndicatorValue(IndicatorPredict.NEUTRAL, resRaw);
    }

    public static IndicatorValue vhf(List<Double> closes) {//Вертикальный горизонтальный фильтр
        double resRaw = TechIndicatorsRaw.vhf(closes);

        if (resRaw >= 80) {
            return new IndicatorValue(IndicatorPredict.SELL, resRaw);
        }
        if (resRaw <= 20) {
            return new IndicatorValue(IndicatorPredict.BUY, resRaw);
        }
        return new IndicatorValue(IndicatorPredict.NEUTRAL, resRaw);
    }

    public static IndicatorValue mfi(List<Double> closes) {//Вертикальный горизонтальный фильтр
        double resRaw = TechIndicatorsRaw.mfi(closes);

        if (resRaw >= 80) {
            return new IndicatorValue(IndicatorPredict.SELL, resRaw);
        }
        if (resRaw <= 20) {
            return new IndicatorValue(IndicatorPredict.BUY, resRaw);
        }
        return new IndicatorValue(IndicatorPredict.NEUTRAL, resRaw);
    }
}
