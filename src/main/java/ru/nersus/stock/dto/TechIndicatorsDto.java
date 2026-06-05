package ru.nersus.stock.dto;

public record TechIndicatorsDto(
        IndicatorValue stochastic,
        IndicatorValue rsi,
        IndicatorValue ema,
        IndicatorValue sma,
        IndicatorValue momentum,
        IndicatorValue generalPredict
) {
//    public IndicatorPredict generalPredict() {
//
//        return IndicatorPredict.BUY;
//    }
}
