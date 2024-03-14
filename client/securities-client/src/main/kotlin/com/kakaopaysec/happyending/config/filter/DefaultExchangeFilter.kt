package com.kakaopaysec.happyending.config.filter

import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import reactor.core.publisher.Mono

class DefaultExchangeFilter : ExchangeFilterFunction {

    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> {
        return next.exchange(request)
    }
}
