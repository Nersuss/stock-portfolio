package ru.nersus.stock.enums;

import lombok.Getter;

public enum PeriodEnum {
    hour(10),
    day(60),
    week(24),
    month(24);

    PeriodEnum(int value) {
        this.value = value;
    }

    @Getter
    private final int value;
}
