package ru.nersus.stock.dto;

public record StockDto(
        String symbol,
        int cost,
        int count
) {
}
