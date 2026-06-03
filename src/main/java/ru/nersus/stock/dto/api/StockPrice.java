package ru.nersus.stock.dto.api;

import java.util.List;

public record StockPrice(
        String secid,
        String boardid,
        Double open,
        Double low,
        Double high,
        Double last,
        Double lastChange,
        Double lastChangePrcnt,
        Double closePrice,
        Double marketPrice,
        Double lcurrentPrice,
        Double issueCapitalization,
        String updateTime
) {
    public static StockPrice fromMarketDataList(List<Object> row) {
        return new StockPrice(
                getString(row, 0),    // SECID
                getString(row, 1),    // BOARDID
                getDouble(row, 9),    // OPEN
                getDouble(row, 10),   // LOW
                getDouble(row, 11),   // HIGH
                getDouble(row, 12),   // LAST
                getDouble(row, 13),   // LASTCHANGE
                getDouble(row, 14),   // LASTCHANGEPRCNT
                getDouble(row, 22),   // CLOSEPRICE
                getDouble(row, 24),   // MARKETPRICE
                getDouble(row, 36),   // LCURRENTPRICE
                getDouble(row, 50),   // ISSUECAPITALIZATION
                getString(row, 32)    // UPDATETIME
        );
    }

    private static String getString(List<Object> row, int index) {
        Object value = index < row.size() ? row.get(index) : null;
        return value != null ? value.toString() : null;
    }

    private static Double getDouble(List<Object> row, int index) {
        Object value = index < row.size() ? row.get(index) : null;
        if (value instanceof Double) return (Double) value;
        if (value instanceof Number) return ((Number) value).doubleValue();
        return null;
    }
}
