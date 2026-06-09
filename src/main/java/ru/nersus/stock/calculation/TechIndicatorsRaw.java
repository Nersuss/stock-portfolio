package ru.nersus.stock.calculation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TechIndicatorsRaw {

    public static double ema(List<Double> prices) {//Расчет экспоненциальной скользящей средней (EMA)
        List<Double> emaValues = new ArrayList<>();
        int period = 10;

        // 1. Рассчитываем множитель (multiplier)
        double k = 2.0 / (period + 1);

        // 2. Первое значение EMA — это простая скользящая средняя (SMA) за первые N дней
        double sum = 0.0;
        for (int i = 0; i < period; i++) {
            sum += prices.get(i);
        }
        double previousEma = sum / period;
        emaValues.add(previousEma);

        // 3. Расчет для всех остальных дней
        for (int i = period; i < prices.size(); i++) {
            double closePrice = prices.get(i);
            double currentEma = (closePrice - previousEma) * k + previousEma;
            emaValues.add(currentEma);
            previousEma = currentEma; // Обновляем предыдущее значение для следующего шага
        }

        return emaValues.getLast();
    }

    public static double sma(List<Double> closePrices) {//Расчет простой скользящей средней (SMA)
        int N = closePrices.size();

        double sma = 0;
        for (Double price : closePrices) {
            sma += price;
        }
        return sma / N;
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

        double emaGrow = ema(grows);
        double emaFall = ema(falls);

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


}
