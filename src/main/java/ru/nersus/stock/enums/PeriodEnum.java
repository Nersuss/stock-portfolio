package ru.nersus.stock.enums;

import lombok.Getter;

public enum PeriodEnum {
    hour(1),
    day(10),
    week(60),
    month(24);

    PeriodEnum(int value) {
        this.value = value;
    }

    @Getter
    private final int value;
}
