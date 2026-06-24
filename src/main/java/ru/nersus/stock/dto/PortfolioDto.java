package ru.nersus.stock.dto;

import java.util.List;

public record PortfolioDto(
        Double cost,
        List<StockDto> stocks,
        Double portfolioChange,
        Double portfolioChangePercent

) {
}
