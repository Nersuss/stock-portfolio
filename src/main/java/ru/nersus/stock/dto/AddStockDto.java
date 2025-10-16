package ru.nersus.stock.dto;

public record AddStockDto(
        String symbol,
        int count
) {
}
