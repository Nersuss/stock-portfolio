package ru.nersus.stock.dto.api;

import com.google.gson.annotations.SerializedName;

public record MatchDto(
        @SerializedName("1. symbol") String symbol,
        @SerializedName("2. name") String name,
        @SerializedName("3. type") String type,
        @SerializedName("4. region") String region,
        @SerializedName("5. marketOpen") String marketOpen,
        @SerializedName("6. marketClose") String marketClose,
        @SerializedName("7. timezone") String timezone,
        @SerializedName("8. currency") String currency,
        @SerializedName("9. matchScore") String matchScore
) {
}
