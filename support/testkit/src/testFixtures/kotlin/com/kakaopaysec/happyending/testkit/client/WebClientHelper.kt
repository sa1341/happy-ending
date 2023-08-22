package com.kakaopaysec.happyending.testkit.client

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val datePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

object WebClientHelper {
    fun createWebClient(): WebClient {
        return WebClient
            .builder()
            .exchangeStrategies(exchangeStrategies(defaultObjectMapper()))
            .clientConnector(
                ReactorClientHttpConnector(httpClient())
            ).build()
    }

    private fun exchangeStrategies(objectMapper: ObjectMapper): ExchangeStrategies {
        return ExchangeStrategies.builder()
            .codecs {
                with(it.defaultCodecs()) {
                    jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
                    jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
                }
            }.build()
    }

    private fun httpClient(): HttpClient {
        return HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
            .responseTimeout(Duration.ofSeconds(15))
            .doOnConnected {
                it.addHandlerLast(ReadTimeoutHandler(10))
                it.addHandlerLast(WriteTimeoutHandler(10))
            }
    }
    private fun defaultObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .registerModule(Jdk8Module())
            .registerModule(customModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
    }

    private fun customModule(): JavaTimeModule = JavaTimeModule().apply {
        addSerializer(LocalDate::class.java, LocalDateSerializer(datePattern))
        addDeserializer(LocalDate::class.java, LocalDateDeserializer(datePattern))
    }
}
