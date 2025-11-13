package ru.nersus.stock.dto.api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockPricesDto {

    @SerializedName("Meta Data")
    MetaDataDto metaDataDto;

    @SerializedName("Time Series (Daily)")
    Map<String, TimeSeriesDto> timeSeriesDto;

    public List<String> getLabels() {
        return timeSeriesDto.keySet().stream().limit(30).toList();
    }

    public List<Double> getOpenPrices() {
        List<Double> openPrices = new ArrayList<>();
        for (TimeSeriesDto dto: timeSeriesDto.values().stream().limit(30).toList()) {
            openPrices.add(dto.open);
        }
        return openPrices;
    }
    public List<TimeSeriesDto> getFullPrices() {
        return new ArrayList<>(timeSeriesDto.values());
    }

}
