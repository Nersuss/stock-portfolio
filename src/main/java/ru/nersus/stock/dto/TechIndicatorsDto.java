package ru.nersus.stock.dto;

import ru.nersus.stock.calculation.IndicatorPredict;

public record TechIndicatorsDto(
        IndicatorValue stochastic,
        IndicatorValue rsi
) {
    public IndicatorPredict generalPredict() {
        return IndicatorPredict.BUY;
    }

    public IndicatorPredict techIndicatorsPredict() {
        return IndicatorPredict.BUY;
    }

    public IndicatorPredict movingAveragesPredict() {
        return IndicatorPredict.BUY;
    }
}
