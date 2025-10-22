package ru.nersus.stock.dto;

import ru.nersus.stock.dto.api.GlobalQuoteDto;

public record StockDto(
        String symbol,
        double cost,
        int count,
        GlobalQuoteDto info
) {
}
