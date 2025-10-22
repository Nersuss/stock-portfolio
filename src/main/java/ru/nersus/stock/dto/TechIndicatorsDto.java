package ru.nersus.stock.dto;

public record TechIndicatorsDto(
        IndicatorValue stochastic,
        IndicatorValue rsi
) {
}
