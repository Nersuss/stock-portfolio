package ru.nersus.stock.dto.api;

import java.util.List;

public record CandlesData(List<List<Object>> data) {
}
