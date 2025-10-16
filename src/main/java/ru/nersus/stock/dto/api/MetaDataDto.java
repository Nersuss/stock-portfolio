package ru.nersus.stock.dto.api;

import com.google.gson.annotations.SerializedName;

public record MetaDataDto(
        @SerializedName("1. Information") String information,
        @SerializedName("2. Symbol") String symbol,
        @SerializedName("3. Last Refreshed") String lastRefreshed,
        @SerializedName("4. Time Zone") String timeZone
) {
}
