package ru.nersus.stock.dto.api;

import java.util.List;

public record Candle(
        Double open,
        Double close,
        Double high,
        Double low,
        Double value,
        Double volume,
        String begin
) {
    public static Candle fromList(List<Object> row) {
        return new Candle(
                getDouble(row, 0),  // open
                getDouble(row, 1),  // close
                getDouble(row, 2),  // high
                getDouble(row, 3),  // low
                getDouble(row, 4),  // value
                getDouble(row, 5),  // volume
                getString(row, 6)   // begin
        );
    }

    private static Double getDouble(List<Object> row, int index) {
        Object value = index < row.size() ? row.get(index) : null;
        if (value instanceof Double) return (Double) value;
        if (value instanceof Number) return ((Number) value).doubleValue();
        return null;
    }

    private static String getString(List<Object> row, int index) {
        Object value = index < row.size() ? row.get(index) : null;
        return value != null ? value.toString() : null;
    }
}