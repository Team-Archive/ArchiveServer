@file:JvmName("DateTimeUtils")
package site.archive.common

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val ASIA_SEOUL_ZONE_ID = "Asia/Seoul"
val yymmddFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yy/MM/dd")
val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
val asiaSeoulZone: ZoneId = ZoneId.of(ASIA_SEOUL_ZONE_ID)

var clock: Clock = Clock.system(asiaSeoulZone)

fun changeClock(date: LocalDate) {
    val dateTime = date.atStartOfDay()
    val zoneOffset = asiaSeoulZone.rules.getOffset(dateTime)
    clock = Clock.fixed(dateTime.atOffset(zoneOffset).toInstant(), zoneOffset)
}

fun fromMilli(milli: Long): ZonedDateTime {
    return Instant.ofEpochMilli(milli).atZone(asiaSeoulZone)
}

fun firstDateTimeOfMonth(): LocalDateTime {
    return YearMonth.now(clock)
        .atDay(1)
        .atTime(0, 0)
}