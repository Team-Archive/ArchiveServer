package com.depromeet.archive.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private DateTimeUtil() {}

    public static String convertToString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yy/MM/dd"));
    }
}
