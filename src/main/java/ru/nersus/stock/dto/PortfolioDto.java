package ru.nersus.stock.dto;

import java.util.List;

public record PortfolioDto(
        Double cost,
        List<StockDto> stocks

) {
}
