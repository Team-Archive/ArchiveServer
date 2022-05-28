package site.archive.util;

import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final DateTimeFormatter YY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern("yy/MM/dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateTimeUtil() {}
}
