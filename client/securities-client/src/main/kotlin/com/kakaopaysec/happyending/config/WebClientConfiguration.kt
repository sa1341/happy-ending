package com.kakaopaysec.happyending.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.kakaopaysec.utils.GuidUtils
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

private val log = KotlinLogging.logger {}
const val APP_USER_ID = "x-app-user-id"

@Configuration
class WebClientConfiguration(
    @Value("\${base.url}")
    val baseUrl: String
) {

    @Bean
    fun happyEndingClient(
        @Qualifier("happyEndingObjectMapper")
        objectMapper: ObjectMapper
    ): WebClient {
        return WebClient
            .builder()
            .exchangeStrategies(exchangeStrategies(objectMapper))
            .baseUrl(baseUrl)
            .clientConnector(
                ReactorClientHttpConnector(createHttpClient())
            )
            .filter { request, next ->
                val clientRequest = ClientRequest.from(request)
                    .header(APP_USER_ID, GuidUtils.get())
                    .build()
                next.exchange(clientRequest)
                    .doOnError {
                        log.error { "Webclient Error: $it" }
                    }
            }
            .build()
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

    private fun createHttpClient(): HttpClient {
        return HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected {
                it.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            }
    }
}
