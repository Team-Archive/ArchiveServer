package site.archive.common;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final DateTimeFormatter YY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern("yy/MM/dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final ZoneId ASIA_SEOUL_ZONE = ZoneId.of("Asia/Seoul");

    private static Clock clock = Clock.system(ASIA_SEOUL_ZONE);

    private DateTimeUtil() {}

    public static void changeClock(LocalDate date) {
        // log.warn("Change clock of DateTimeUtil. It may effect now() methods of DateTimeUtil.");
        var dateTime = date.atStartOfDay();
        var zoneOffset = ASIA_SEOUL_ZONE.getRules().getOffset(dateTime);
        clock = Clock.fixed(dateTime.atOffset(zoneOffset).toInstant(), zoneOffset);
    }

    public static ZonedDateTime fromMilli(long milli) {
        return Instant.ofEpochMilli(milli).atZone(ASIA_SEOUL_ZONE);
    }

    public static LocalDateTime firstDateTimeOfMonth() {
        return YearMonth.now(clock)
                        .atDay(1)
                        .atTime(0, 0);
    }

}
