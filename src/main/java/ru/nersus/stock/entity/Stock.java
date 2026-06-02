package ru.nersus.stock.entity;

public record Stock(
        Integer id,
        String symbol,
        int count,
        int ownerId
) {
}
