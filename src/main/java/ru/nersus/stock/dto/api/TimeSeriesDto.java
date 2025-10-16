package ru.nersus.stock.dto.api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSeriesDto {
    @SerializedName("1. open")
    Double open;

    @SerializedName("2. high")
    Double high;

    @SerializedName("3. low")
    Double low;

    @SerializedName("4. close")
    Double close;

    @SerializedName("5. volume")
    String volume;
}
