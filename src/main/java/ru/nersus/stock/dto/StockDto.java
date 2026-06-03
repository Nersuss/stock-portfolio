package ru.nersus.stock.dto;

import ru.nersus.stock.dto.api.SecurityDescription;

public record StockDto(
        int id,
        String symbol,
        double cost,
        int count,
        SecurityDescription info
) {
}
