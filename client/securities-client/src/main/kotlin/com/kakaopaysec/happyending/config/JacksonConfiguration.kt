package com.kakaopaysec.happyending.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kakaopaysec.utils.DateTimeUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.LocalDate
import java.time.LocalDateTime

@Configuration
class JacksonConfiguration {

    @Bean
    @Primary
    fun happyEndingObjectMapper(): ObjectMapper {
        val module = JavaTimeModule()
        module.addSerializer(LocalDate::class.java, LocalDateSerializer(DateTimeUtils.yyyy_MM_dd))
        module.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(DateTimeUtils.yyyy_MM_dd_HH_mm_ss))
        module.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeUtils.yyyy_MM_dd_HH_mm_ss))
        return jacksonObjectMapper()
            .registerModule(Jdk8Module())
            .registerModule(module)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
    }
}
