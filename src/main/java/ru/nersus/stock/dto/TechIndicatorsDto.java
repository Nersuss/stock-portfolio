package ru.nersus.stock.dto;

import ru.nersus.stock.calculation.IndicatorPredict;

import java.util.ArrayList;
import java.util.List;

public record TechIndicatorsDto(
        List<IndicatorValue> emas,
        List<IndicatorValue> smas,
        IndicatorValue stochastic,
        IndicatorValue rsi,
        IndicatorValue momentum,
        IndicatorValue williams,
        IndicatorValue vhf,
        IndicatorValue mfi,
        IndicatorValue massIndex,

        IndicatorValue generalPredict
) {

    public TechIndicatorsDto(List<IndicatorValue> emas,
                             List<IndicatorValue> smas,
                             IndicatorValue stochastic,
                             IndicatorValue rsi,
                             IndicatorValue momentum,
                             IndicatorValue williams,
                             IndicatorValue vhf,
                             IndicatorValue mfi,
                             IndicatorValue massIndex) {
        this(emas, smas, stochastic, rsi, momentum, williams, vhf, mfi, massIndex,
                calculateGeneralPredict(mergeAllIndicators(smas, emas, stochastic, rsi, momentum, williams, vhf, mfi, massIndex)));
    }

    private static IndicatorValue[] mergeAllIndicators(
            List<IndicatorValue> smaValues,
            List<IndicatorValue> emaValues,
            IndicatorValue... otherIndicators) {

        List<IndicatorValue> all = new ArrayList<>();
        all.addAll(smaValues);
        all.addAll(emaValues);

        for (IndicatorValue indicator : otherIndicators) {
            if (indicator != null) {
                all.add(indicator);
            }
        }

        return all.toArray(new IndicatorValue[0]);
    }

    private static IndicatorValue calculateGeneralPredict(IndicatorValue... indicators) {
        int buyCount = 0;
        int sellCount = 0;
        int holdCount = 0;
        int totalCount = 0;

        for (IndicatorValue indicator : indicators) {
            if (indicator != null && indicator.predict() != null) {
                totalCount++;
                switch (indicator.predict()) {
                    case BUY -> buyCount++;
                    case SELL -> sellCount++;
                    case NEUTRAL -> holdCount++;
                }
            }
        }

        if (totalCount == 0) {
            return new IndicatorValue(IndicatorPredict.NEUTRAL, null);
        }

        IndicatorPredict generalPredict;

        if (buyCount > totalCount / 2.0) {
            generalPredict = IndicatorPredict.BUY;
        } else if (sellCount > totalCount / 2.0) {
            generalPredict = IndicatorPredict.SELL;
        } else if (buyCount > sellCount) {
            generalPredict = IndicatorPredict.BUY;
        } else if (sellCount > buyCount) {
            generalPredict = IndicatorPredict.SELL;
        } else {
            generalPredict = IndicatorPredict.NEUTRAL;
        }

        double strength = (double) Math.max(buyCount, Math.max(sellCount, holdCount)) / totalCount;

        return new IndicatorValue(generalPredict, strength);
    }
}