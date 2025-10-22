package ru.nersus.stock.dto;

import ru.nersus.stock.dto.api.GlobalQuoteDto;

import java.util.List;

public record LandingDto(
        GlobalQuoteDto stockInfo,
        List<String> stockDateLabels,
        List<Double> stockOpenPrices,
        TechIndicatorsDto techIndicators
) {
}
