package vgu.SoSe2026_Compnet2.util;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for formatting dates in a consistent way throughout the application.
 * Formats dates to the pattern "yyyy-MM-dd HH:mm:ss".
 */
public final class DateFormatter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Format time to string.
     * @param dateTime The LocalDateTime to format.
     * @return String represent date and time in the format "yyyy-MM-dd HH:mm:ss".
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    /**
     * Format time to string.
     * @param date The timestamp to format.
     * @return String represent date and time in the format "yyyy-MM-dd HH:mm:ss".
     */
    public static String format(long date) {
        LocalDateTime localDateTime = new Date(date).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(FORMATTER);
    }

    /**
    * Format current time to string.
    * @return String current time in the format "yyyy-MM-dd HH:mm:ss".
    */
    public static String now() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
