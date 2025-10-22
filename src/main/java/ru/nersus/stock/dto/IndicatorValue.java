package ru.nersus.stock.dto;

import ru.nersus.stock.calculation.IndicatorPredict;

public record IndicatorValue(
        IndicatorPredict predict,
        Double rawValue
) {
    public IndicatorValue(IndicatorPredict predict, Double rawValue) {
        this.predict = predict;
        this.rawValue = Math.floor(rawValue * 100) / 100;
    }
}
