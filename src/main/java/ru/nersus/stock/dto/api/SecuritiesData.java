package ru.nersus.stock.dto.api;

import java.util.List;

public record SecuritiesData(
        List<List<Object>> data) {
}
