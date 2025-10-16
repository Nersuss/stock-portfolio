package ru.nersus.stock.dto.api;

import com.google.gson.annotations.SerializedName;

public record GlobalQuoteDto(
        @SerializedName("01. symbol") String symbol,
        @SerializedName("02. open") Double open,
        @SerializedName("03. high") Double high,
        @SerializedName("04. low") Double low,
        @SerializedName("05. price") Double price,
        @SerializedName("06. volume") Long volume,
        @SerializedName("07. latest trading day") String latestTradingDay,
        @SerializedName("08. previous close") Double previousClose,
        @SerializedName("09. change") Double change,
        @SerializedName("10. change percent") String changePercent
) {
}
