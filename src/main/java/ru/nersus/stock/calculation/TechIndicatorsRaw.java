package ru.nersus.stock.calculation;

import java.util.*;

public class TechIndicatorsRaw {

    public static Map<Integer, Double> calculateSMA(List<Double> closePrices, int... periods) {
        Map<Integer, Double> results = new HashMap<>();
        for (int period : periods) {
            if (closePrices.size() >= period) {
                List<Double> lastNPrices = closePrices.subList(
                        closePrices.size() - period,
                        closePrices.size()
                );
                double sma = sma(lastNPrices);
                results.put(period, sma);
            }
        }
        return results;
    }

    public static double sma(List<Double> prices) {
        return prices.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public static Map<Integer, Double> calculateEMA(List<Double> closePrices, int... periods) {
        Map<Integer, Double> results = new HashMap<>();
        for (int period : periods) {
            if (closePrices.size() >= period) {
                int neededSize = period * 2;
                List<Double> recentPrices;

                if (closePrices.size() >= neededSize) {
                    recentPrices = closePrices.subList(
                            closePrices.size() - neededSize,
                            closePrices.size()
                    );
                } else {
                    recentPrices = closePrices.subList(
                            closePrices.size() - period,
                            closePrices.size()
                    );
                }

                double ema = ema(recentPrices, period);
                results.put(period, ema);
            }
        }
        return results;
    }

    public static double ema(List<Double> prices, int period) {
        if (prices.size() < period) {
            throw new IllegalArgumentException("Недостаточно данных для периода " + period);
        }

        double multiplier = 2.0 / (period + 1);

        double sum = 0.0;
        for (int i = 0; i < period; i++) {
            sum += prices.get(i);
        }
        double ema = sum / period;

        for (int i = period; i < prices.size(); i++) {
            ema = (prices.get(i) - ema) * multiplier + ema;
        }

        return ema;
    }

    public static double rsi(List<Double> prices) {
        int N = prices.size();
        List<Double> grows = new ArrayList<>(N);
        List<Double> falls = new ArrayList<>(N);

        for (int i = 0; i < N; i++) {
            grows.add(0.0);
            falls.add(0.0);
        }

        for (int i = 0; i < N - 1; i++) {
            if (prices.get(i) < prices.get(i + 1)) {
                falls.set(i, 0.0);
                grows.set(i, prices.get(i + 1) - prices.get(i));
            } else {
                grows.set(i, 0.0);
                falls.set(i, prices.get(i) - prices.get(i + 1));
            }
        }

        double emaGrow = ema(grows, N);
        double emaFall = ema(falls, N);

        if (emaFall == 0) {
            return 100.0;
        }

        double rs = emaGrow / emaFall;
        return 100 - (100 / (1 + rs));
    }

    public static double stochastic(List<Double> low, List<Double> high, double close) {//Стохастический осциллятор
        double lowest = low.stream().min(Comparator.naturalOrder()).orElseThrow();
        double highest = high.stream().max(Comparator.naturalOrder()).orElseThrow();

        return ((close - lowest) / (highest - lowest)) * 100;
    }

    public static double momentum(double close, double oldClose) {//Моментум
        return (close - oldClose) * 100;
    }

    public static double williams(List<Double> low, List<Double> high, double close) {//Процентный диапазон Уильямса (%R)
        double lowest = low.stream().min(Comparator.naturalOrder()).orElseThrow();
        double highest = high.stream().max(Comparator.naturalOrder()).orElseThrow();

        return ((highest - close) / (highest - lowest)) * -100;
    }

    public static double vhf(List<Double> closes) {//Вертикальный горизонтальный фильтр
        if (closes == null || closes.size() < 2) {
            return 0.0;
        }

        int period = closes.size();

        double highestClose = Collections.max(closes);
        double lowestClose = Collections.min(closes);

        double numerator = Math.abs(highestClose - lowestClose);

        double denominator = 0.0;
        for (int i = 1; i < closes.size(); i++) {
            denominator += Math.abs(closes.get(i) - closes.get(i - 1));
        }

        if (denominator == 0) {
            return 0.0;
        }

        return numerator / denominator;
    }

    public static double mfi(List<Double> highs, List<Double> lows, List<Double> closes, List<Double> volumes, int period) {//Денежных потоков индекс
        if (highs == null || lows == null || closes == null || volumes == null) {
            return 0.0;
        }
        if (highs.size() < period + 1 || lows.size() < period + 1 ||
                closes.size() < period + 1 || volumes.size() < period + 1) {
            return 0.0;
        }

        List<Double> positiveMoneyFlow = new ArrayList<>();
        List<Double> negativeMoneyFlow = new ArrayList<>();

        for (int i = 1; i < closes.size(); i++) {
            double typicalPrice = (highs.get(i) + lows.get(i) + closes.get(i)) / 3.0;
            double previousTypicalPrice = (highs.get(i - 1) + lows.get(i - 1) + closes.get(i - 1)) / 3.0;

            double moneyFlow = typicalPrice * volumes.get(i);

            if (typicalPrice > previousTypicalPrice) {
                positiveMoneyFlow.add(moneyFlow);
                negativeMoneyFlow.add(0.0);
            } else if (typicalPrice < previousTypicalPrice) {
                positiveMoneyFlow.add(0.0);
                negativeMoneyFlow.add(moneyFlow);
            } else {
                positiveMoneyFlow.add(0.0);
                negativeMoneyFlow.add(0.0);
            }
        }

        int startIndex = Math.max(0, positiveMoneyFlow.size() - period);

        double sumPositiveFlow = 0.0;
        double sumNegativeFlow = 0.0;

        for (int i = startIndex; i < positiveMoneyFlow.size(); i++) {
            sumPositiveFlow += positiveMoneyFlow.get(i);
            sumNegativeFlow += negativeMoneyFlow.get(i);
        }

        if (sumNegativeFlow == 0) {
            return 100.0;
        }

        double moneyRatio = sumPositiveFlow / sumNegativeFlow;
        double mfi = 100.0 - (100.0 / (1.0 + moneyRatio));

        return mfi;
    }

    public static double massIndex(List<Double> highs, List<Double> lows) {
        if (highs == null || lows == null || highs.size() < 25 || lows.size() < 25) {
            return 0.0;
        }

        int emaPeriod = 9;

        List<Double> ranges = new ArrayList<>();
        for (int i = 0; i < highs.size(); i++) {
            ranges.add(highs.get(i) - lows.get(i));
        }

        List<Double> emaRanges = new ArrayList<>();
        double multiplier = 2.0 / (emaPeriod + 1);

        double sum = 0.0;
        for (int i = 0; i < emaPeriod; i++) {
            sum += ranges.get(i);
        }
        double ema = sum / emaPeriod;
        emaRanges.add(ema);

        for (int i = emaPeriod; i < ranges.size(); i++) {
            ema = (ranges.get(i) - ema) * multiplier + ema;
            emaRanges.add(ema);
        }

        List<Double> emaEmaRanges = new ArrayList<>();
        sum = 0.0;
        for (int i = 0; i < emaPeriod; i++) {
            sum += emaRanges.get(i);
        }
        double emaEma = sum / emaPeriod;
        emaEmaRanges.add(emaEma);

        for (int i = emaPeriod; i < emaRanges.size(); i++) {
            emaEma = (emaRanges.get(i) - emaEma) * multiplier + emaEma;
            emaEmaRanges.add(emaEma);
        }

        List<Double> ratios = new ArrayList<>();
        int startIndex = emaEmaRanges.size() - emaRanges.size();

        for (int i = 0; i < emaEmaRanges.size(); i++) {
            int rangeIndex = i + startIndex;
            if (rangeIndex >= 0 && rangeIndex < emaRanges.size() && emaEmaRanges.get(i) != 0) {
                ratios.add(emaRanges.get(rangeIndex) / emaEmaRanges.get(i));
            }
        }

        if (ratios.size() < 25) {
            return 0.0;
        }

        double massIndex = 0.0;
        int lastIndex = ratios.size();
        for (int i = lastIndex - 25; i < lastIndex; i++) {
            massIndex += ratios.get(i);
        }

        return massIndex;
    }

}
