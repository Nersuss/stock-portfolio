package ru.nersus.stock.dto.api;

import java.util.List;

public record MarketDataBlock(List<List<Object>> data) {
}
