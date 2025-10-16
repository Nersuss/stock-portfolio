package ru.nersus.stock.dto;

import ru.nersus.stock.calculation.IndicatorValue;

public record TechIndicatorsDto(
        IndicatorValue stochastic,
        IndicatorValue rsi
) {
}
