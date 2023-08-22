package com.kakaopaysec.happyending.common.error

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.netty.channel.ConnectTimeoutException
import io.netty.handler.timeout.ReadTimeoutException
import org.springframework.web.reactive.function.client.WebClientRequestException
import java.util.concurrent.TimeoutException

class SecuritiesErrorMapper {
    companion object {
        fun onErrorMap(throwable: Throwable) =
            when (throwable) {
                is TimeoutException, is ConnectTimeoutException -> SecuritiesException.of(
                    securitiesErrorCode = SecuritiesErrorCode.SECURITIES_TIME_OUT,
                    cause = throwable
                )
                is CallNotPermittedException -> SecuritiesException.of(
                    securitiesErrorCode = SecuritiesErrorCode.SECURITIES_UNAVAILABLE,
                    cause = throwable
                )
                is WebClientRequestException -> {
                    if (throwable.cause is ReadTimeoutException) {
                        SecuritiesException.of(
                            securitiesErrorCode = SecuritiesErrorCode.SECURITIES_TIME_OUT,
                            cause = throwable
                        )
                    } else {
                        throwable
                    }
                }
                else -> throwable
            }
    }
}
