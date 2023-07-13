package com.kakaopaysec.happyending

import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeSerializerTest {

    @Test
    fun `localDateTime 객체를 직렬화한다`() {
        // given
        val value = LocalDateTime.now()
        println("LocalDateTime: $value")
        val timestamp = value.atZone(ZoneId.systemDefault()).toInstant().epochSecond * 1000
        // when
        println("timestamp: $timestamp")
        println(Instant.ofEpochSecond(timestamp))
    }
}
