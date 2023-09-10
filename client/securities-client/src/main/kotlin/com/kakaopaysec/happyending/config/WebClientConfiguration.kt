package com.kakaopaysec.happyending.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.kakaopaysec.happyending.utils.GuidUtils
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
import org.springframework.http.client.reactive.ReactorResourceFactory
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import reactor.netty.transport.ProxyProvider
import java.net.URI
import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.function.Predicate

private val log = KotlinLogging.logger {}
const val APP_USER_ID = "x-app-user-id"

@Configuration
class WebClientConfiguration(
    @Value("\${happyending.webclient.timeout:5}") private val timeout: Long,
    @Value("\${happyending.webclient.max-connection:1024}") private val maxConnection: Int,
    @Value("\${base.url}")
    val baseUrl: String,
    private val reactorResourceFactory: ReactorResourceFactory
) {

    @Bean
    fun happyEndingClient(
        @Qualifier("happyEndingObjectMapper")
        happyEndingObjectMapper: ObjectMapper
    ): WebClient {
        return builder()
            .exchangeStrategies(exchangeStrategies(happyEndingObjectMapper))
            .baseUrl(baseUrl)
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

    private fun builder(externalGwUrl: String? = null, maxIdleTime: Long = 8, maxLifeTime: Long = 8) =
        WebClient
            .builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    reactorResourceFactory.apply {
                        connectionProvider = ConnectionProvider.builder("happyending")
                            .maxConnections(maxConnection)
                            .maxIdleTime(Duration.ofSeconds(maxIdleTime))
                            .maxLifeTime(Duration.ofSeconds(maxLifeTime))
                            .pendingAcquireTimeout(Duration.ofSeconds(3))
                            .lifo()
                            .build()
                    }
                ) { httpClient: HttpClient ->
                    httpClient
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3_000)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .doOnConnected { conn ->
                            conn.addHandlerFirst(ReadTimeoutHandler(timeout, TimeUnit.SECONDS))
                            conn.addHandlerFirst(WriteTimeoutHandler(timeout, TimeUnit.SECONDS))
                        }.let {
                            if (!externalGwUrl.isNullOrBlank()) {
                                it.proxy { proxyProvider ->
                                    runCatching {
                                        val proxyUrl = URI.create(externalGwUrl)
                                        proxyProvider.type(ProxyProvider.Proxy.HTTP).host(proxyUrl.host)
                                            .port(proxyUrl.port)
                                            .nonProxyHostsPredicate(Predicate.isEqual("localhost"))
                                    }.getOrDefault(it)
                                }
                            } else {
                                it
                            }
                        }
                        .keepAlive(true)
                }
            )
}
