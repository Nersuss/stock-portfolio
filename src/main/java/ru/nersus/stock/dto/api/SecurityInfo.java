package ru.nersus.stock.dto.api;

import java.util.List;

public record SecurityInfo(
        String secid,
        String shortname,
        String regnumber,
        String name,
        String isin,
        Integer isTraded,
        Integer emitentId,
        String emitentTitle,
        String emitentInn,
        String emitentOkpo,
        String type,
        String group,
        String primaryBoardid,
        String marketpriceBoardid
) {
    public static SecurityInfo fromList(List<Object> row) {
        return new SecurityInfo(
                getString(row, 0),
                getString(row, 1),
                getString(row, 2),
                getString(row, 3),
                getString(row, 4),
                getInteger(row, 5),
                getInteger(row, 6),
                getString(row, 7),
                getString(row, 8),
                getString(row, 9),
                getString(row, 10),
                getString(row, 11),
                getString(row, 12),
                getString(row, 13)
        );
    }
    private static String getString(List<Object> row, int index) {
        Object value = index < row.size() ? row.get(index) : null;
        return value != null ? value.toString() : null;
    }

    private static Integer getInteger(List<Object> row, int index) {
        Object value = index < row.size() ? row.get(index) : null;
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return null;
    }
}
