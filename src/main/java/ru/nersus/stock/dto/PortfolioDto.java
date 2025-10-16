package ru.nersus.stock.dto;

import java.util.List;

public record PortfolioDto(
        double cost,
        List<StockDto> stocks

        ) {
}
