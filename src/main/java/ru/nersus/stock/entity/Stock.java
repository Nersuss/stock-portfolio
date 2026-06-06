package ru.nersus.stock.entity;

public record Stock(
        Integer id,
        String symbol,
        String shortName,
        int count,
        int ownerId
) {
}
