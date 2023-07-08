package com.kakaopaysec.utils

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.SignStyle
import java.time.temporal.ChronoField

object DateTimeUtils {
    val yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val yyyy_MM_dd_HH_mm_ss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val yyyy_MM_dd_HH_mm_ss_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

    val yyMMdd = DateTimeFormatter.ofPattern("yyMMdd")

    val yyyyMMdd = DateTimeFormatterBuilder()
        .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
        .appendValue(ChronoField.MONTH_OF_YEAR, 2)
        .appendValue(ChronoField.DAY_OF_MONTH, 2)
        .toFormatter()

    val yyyyMMddHHmmss = DateTimeFormatterBuilder()
        .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
        .appendValue(ChronoField.MONTH_OF_YEAR, 2)
        .appendValue(ChronoField.DAY_OF_MONTH, 2)
        .appendValue(ChronoField.HOUR_OF_DAY, 2)
        .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
        .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
        .toFormatter()
}
