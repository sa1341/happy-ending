package com.kakaopaysec.happyending.config

import com.kakaopaysec.happyending.exception.SecuritiesException
import com.kakaopaysec.happyending.service.ServiceType
import mu.KotlinLogging
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

private val log = KotlinLogging.logger {}

class CustomRetry {
    companion object {
        private const val MAX_RETRY_COUNT = 3
        private const val RETRY_DELAY = 1000L // millisecond, 재시도하기까지의 시간

        // 재시도를 중단 시키려면 throw exception을 하면 된다.
        fun create(serviceType: ServiceType): Retry {
            return Retry.from { response ->
                response.map {
                    log.debug { "Retry Count: ${it.totalRetries()}" }
                    if (it.totalRetries() + 1 < MAX_RETRY_COUNT) {
                        log.debug { it.failure().javaClass.name }
                        when (it.failure()) {
                            is SecuritiesException -> {
                                if (checkRetry(it.failure() as SecuritiesException, serviceType)) {
                                    throw it.failure()
                                }
                            }
                        }
                    } else {
                        throw it.failure()
                    }

                    Mono.delay(Duration.ofMillis(RETRY_DELAY))
                }
            }
        }

        private fun checkRetry(exception: SecuritiesException, serviceType: ServiceType): Boolean {
            log.debug { "serviceType isRetry: ${serviceType.isRetry}" }
            return exception.httpStatus.is4xxClientError || !serviceType.isRetry
        }
    }
}
