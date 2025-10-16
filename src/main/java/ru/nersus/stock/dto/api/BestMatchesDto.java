package ru.nersus.stock.dto.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record BestMatchesDto(
        @SerializedName("bestMatches") List<MatchDto> bestMatches
) {
}
