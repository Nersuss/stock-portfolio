package ru.nersus.stock.calculation;

import lombok.Getter;

public enum IndicatorPredict {
    BUY("Покупать"),
    SELL("Продавать"),
    NEUTRAL("Нейтрально");

    IndicatorPredict(String value) {
        this.value = value;
    }

    @Getter
    private final String value;
}
