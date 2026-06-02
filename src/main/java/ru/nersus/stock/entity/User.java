package ru.nersus.stock.entity;

public record User (
        Integer id,
        String email,
        String password
) {
}