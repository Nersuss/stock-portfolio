package ru.nersus.stock.dto;

import ru.nersus.stock.calculation.IndicatorPredict;

public record TechIndicatorsDto(
        IndicatorValue stochastic,
        IndicatorValue rsi,
        IndicatorValue ema,
        IndicatorValue sma,
        IndicatorValue momentum,
        IndicatorValue williams,
        IndicatorValue vhf,
        IndicatorValue mfi,
//        IndicatorValue massIndex,

        IndicatorValue generalPredict
) {

    public TechIndicatorsDto(IndicatorValue stochastic,
                             IndicatorValue rsi,
                             IndicatorValue ema,
                             IndicatorValue sma,
                             IndicatorValue momentum,
                             IndicatorValue williams,
                             IndicatorValue vhf,
                             IndicatorValue mfi) {
        this(stochastic, rsi, ema, sma, momentum, williams, vhf, mfi,
                calculateGeneralPredict(stochastic, rsi, ema, sma, momentum, williams, vhf, mfi));
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