package vgu.SoSe2026_Compnet2.service;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateFormatter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    public static String format(long date) {
        LocalDateTime localDateTime = new Date(date).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(FORMATTER);
    }

    public static String now() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
