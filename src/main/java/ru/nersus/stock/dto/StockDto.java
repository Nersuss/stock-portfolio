package ru.nersus.stock.dto;

import ru.nersus.stock.calculation.IndicatorPredict;
import ru.nersus.stock.dto.api.SecurityDescription;

public record StockDto(
        int id,
        String symbol,
        String shortname,
        double cost,
        Integer count,
        String wallet,
        IndicatorPredict predict,
        SecurityDescription info
) {
}
