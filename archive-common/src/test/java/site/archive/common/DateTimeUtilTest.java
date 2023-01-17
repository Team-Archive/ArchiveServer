package site.archive.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilTest {

    private final LocalDate dummyDate = LocalDate.of(1995, 9, 25);
    private final LocalTime dummyTime = LocalTime.of(11, 11, 11);
    private final LocalDateTime dummyDateTime = LocalDateTime.of(dummyDate, dummyTime);

    @Test
    void YY_MM_DD_formatterTest() {
        var convertedDate = dummyDate.format(DateTimeUtils.getYymmddFormatter());
        assertThat(convertedDate).isEqualTo("95/09/25");
    }

    @Test
    void DATE_TIME_formatterTest() {
        var convertedDateTime = dummyDateTime.format(DateTimeUtils.getDateTimeFormatter());
        assertThat(convertedDateTime).isEqualTo("1995-09-25 11:11:11");
    }

    @Test
    void dateOfMonthTest() {
        DateTimeUtils.changeClock(LocalDate.of(2022, 7, 2));
        var julFirstDate = DateTimeUtils.firstDateTimeOfMonth();
        assertThat(julFirstDate).isEqualTo(LocalDateTime.of(2022, 7, 1, 0, 0));

        var augFirstDate = julFirstDate.plusMonths(1);
        DateTimeUtils.changeClock(LocalDate.of(2022, 8, 22));
        assertThat(augFirstDate).isEqualTo(DateTimeUtils.firstDateTimeOfMonth());
    }

}