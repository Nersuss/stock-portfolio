package ru.nersus.stock.dto;

import ru.nersus.stock.dto.api.Candle;
import ru.nersus.stock.dto.api.SecurityDescription;
import ru.nersus.stock.dto.api.StockPrice;

import java.util.List;

public record LandingDto(
        SecurityDescription info,
        StockPrice price,
        List<Candle> candles,
        TechIndicatorsDto indicators
) {
}
