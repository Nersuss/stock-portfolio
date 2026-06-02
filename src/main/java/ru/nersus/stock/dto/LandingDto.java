package ru.nersus.stock.dto;

import ru.nersus.stock.dto.api.GlobalQuoteDto;

import java.util.List;

public record LandingDto(
        GlobalQuoteDto info,
        List<String> dates,
        List<Double> prices,
        TechIndicatorsDto indicators
) {
}
