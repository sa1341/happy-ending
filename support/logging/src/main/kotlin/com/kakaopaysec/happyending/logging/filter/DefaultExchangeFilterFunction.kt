package com.kakaopaysec.happyending.logging.filter

import com.kakaopaysec.happyending.logging.logger.ApiJsonLogger
import org.reactivestreams.Publisher
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.client.reactive.ClientHttpRequestDecorator
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

class DefaultExchangeFilterFunction(
    private val apiJsonLogger: ApiJsonLogger
) : ExchangeFilterFunction {

    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> {
        val requestTime = LocalDateTime.now()
        var requestBody: String? = null

        val filteredRequest = ClientRequest.from(request)
            .body { outputMessage, context ->
                request.body().insert(
                    object : ClientHttpRequestDecorator(outputMessage) {
                        override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> {
                            return super.writeWith(
                                Mono.from(body).doOnNext { buffer ->
                                    requestBody = buffer.toString(StandardCharsets.UTF_8)
                                }
                            )
                        }
                    },
                    context
                )
            }.build()

        return next.exchange(filteredRequest).map { clientResponse ->
            clientResponse.mutate().body { bodyFlux ->
                bodyFlux.doOnNext { responseBody ->
                    apiJsonLogger.info(
                        startTime = requestTime,
                        request = filteredRequest,
                        requestBody = requestBody ?: "",
                        response = clientResponse,
                        responseBody = responseBody?.toString(StandardCharsets.UTF_8) ?: ""
                    )
                }
            }.build()
        }.doOnError {
            apiJsonLogger.error(
                startTime = requestTime,
                request = filteredRequest,
                requestBody = requestBody ?: "",
                errorMessage = it.message ?: "Unknown error"
            )
        }
    }
}
