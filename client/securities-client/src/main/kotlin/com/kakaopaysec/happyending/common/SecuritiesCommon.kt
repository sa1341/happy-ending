package com.kakaopaysec.happyending.common

import com.kakaopaysec.happyending.exception.SecuritiesErrorCode
import com.kakaopaysec.happyending.exception.SecuritiesException
import io.netty.channel.ConnectTimeoutException
import io.netty.handler.timeout.ReadTimeoutException
import org.springframework.core.codec.DecodingException
import org.springframework.web.reactive.function.client.WebClientRequestException
import java.util.concurrent.TimeoutException

class SecuritiesCommon {
    companion object {
        fun onErrorMap(throwable: Throwable) =
            when (throwable) {
                is TimeoutException, is ConnectTimeoutException -> SecuritiesException.of(
                    securitiesErrorCode = SecuritiesErrorCode.LEDGER_TIME_OUT,
                    cause = throwable
                )
                is DecodingException -> SecuritiesException.of(
                    securitiesErrorCode = SecuritiesErrorCode.LEDGER_PARSING_ERROR,
                    cause = throwable
                )
                is WebClientRequestException -> {
                    if (throwable.cause is ReadTimeoutException) {
                        SecuritiesException.of(
                            securitiesErrorCode = SecuritiesErrorCode.LEDGER_TIME_OUT,
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
