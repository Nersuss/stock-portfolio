package ru.nersus.stock.calculation;

import java.util.*;

public class TechIndicatorsRaw {

    public static Map<Integer, Double> calculateSMA(List<Double> closePrices, int... periods) {
        Map<Integer, Double> results = new HashMap<>();
        for (int period : periods) {
            if (closePrices.size() >= period) {
                // Берем последние N значений
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
                // Береем последние N + period значений для расчета EMA
                // Нужно period значений для инициализации + еще немного для расчета
                int neededSize = period * 2; // минимум period для SMA + period для EMA
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

        // Первое значение EMA = SMA за первые period значений из переданного списка
        double sum = 0.0;
        for (int i = 0; i < period; i++) {
            sum += prices.get(i);
        }
        double ema = sum / period;

        // Расчет EMA для остальных значений
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
        double highClose = Collections.max(closes);
        double lowClose = Collections.min(closes);

        return 0;
    }

    public static double mfi(List<Double> closes) {//Денежных потоков индекс
        double highClose = Collections.max(closes);
        double lowClose = Collections.min(closes);

        return 0;
    }


}
