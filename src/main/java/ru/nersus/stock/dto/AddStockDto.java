package ru.nersus.stock.dto;

public record AddStockDto(
        String symbol,
        String shortname,
        int count
) {
}
