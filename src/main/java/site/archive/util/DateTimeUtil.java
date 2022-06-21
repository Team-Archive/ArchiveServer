package site.archive.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final DateTimeFormatter YY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern("yy/MM/dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final ZoneId ASIA_ZONE = ZoneId.of("Asia/Seoul");

    private DateTimeUtil() {}

    public static ZonedDateTime fromMilli(long milli) {
        return Instant.ofEpochMilli(milli).atZone(ASIA_ZONE);
    }

}
