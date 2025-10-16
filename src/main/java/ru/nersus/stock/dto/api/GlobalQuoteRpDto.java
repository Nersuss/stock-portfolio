package ru.nersus.stock.dto.api;

import com.google.gson.annotations.SerializedName;

public record GlobalQuoteRpDto(
        @SerializedName("Global Quote") GlobalQuoteDto globalQuote
) {
}
