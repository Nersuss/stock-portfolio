package ru.nersus.stock.dto.api;

import java.util.List;
import java.util.Map;

public record SecurityDescription(
        String secid,
        String issueName,
        String name,
        String shortname,
        String isin,
        String regnumber,
        Long issueSize,
        Double facevalue,
        String faceUnit,
        String issueDate,
        String latName,
        Boolean hasProspectus,
        String decisionDate,
        Boolean hasDefault,
        Boolean hasTechnicalDefault,
        Integer emitentMismatchCur,
        Integer listLevel,
        Boolean isQualifiedInvestors,
        Boolean morningSession,
        Boolean eveningSession,
        Boolean weekendSession,
        String registryDate,
        String typename,
        String group,
        String type,
        String groupName,
        String emitterId
) {
    public static SecurityDescription fromDataList(List<List<Object>> data) {
        Map<String, String> values = new java.util.HashMap<>();

        // Преобразуем data в Map по ключу name
        for (List<Object> row : data) {
            if (row.size() >= 3) {
                String key = getString(row, 0);  // name
                String value = getString(row, 2); // value
                values.put(key, value);
            }
        }

        return new SecurityDescription(
                values.get("SECID"),
                values.get("ISSUENAME"),
                values.get("NAME"),
                values.get("SHORTNAME"),
                values.get("ISIN"),
                values.get("REGNUMBER"),
                getLong(values.get("ISSUESIZE")),
                getDouble(values.get("FACEVALUE")),
                values.get("FACEUNIT"),
                values.get("ISSUEDATE"),
                values.get("LATNAME"),
                getBoolean(values.get("HASPROSPECTUS")),
                values.get("DECISIONDATE"),
                getBoolean(values.get("HASDEFAULT")),
                getBoolean(values.get("HASTECHNICALDEFAULT")),
                getInteger(values.get("EMITENTMISMATCHCUR")),
                getInteger(values.get("LISTLEVEL")),
                getBoolean(values.get("ISQUALIFIEDINVESTORS")),
                getBoolean(values.get("MORNINGSESSION")),
                getBoolean(values.get("EVENINGSESSION")),
                getBoolean(values.get("WEEKENDSESSION")),
                values.get("REGISTRY_DATE"),
                values.get("TYPENAME"),
                values.get("GROUP"),
                values.get("TYPE"),
                values.get("GROUPNAME"),
                values.get("EMITTER_ID")
        );
    }

    private static String getString(List<Object> row, int index) {
        Object value = index < row.size() ? row.get(index) : null;
        return value != null ? value.toString() : null;
    }

    private static Long getLong(String value) {
        try {
            return value != null ? Long.parseLong(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Double getDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Integer getInteger(String value) {
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Boolean getBoolean(String value) {
        if (value == null) return null;
        return "1".equals(value) || "true".equalsIgnoreCase(value);
    }
}