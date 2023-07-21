package com.kakaopaysec.happyending.service

import com.kakaopaysec.happyending.common.SecuritiesCommon
import com.kakaopaysec.happyending.config.CustomRetry
import com.kakaopaysec.happyending.exception.SecuritiesErrorCode
import com.kakaopaysec.happyending.exception.SecuritiesException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI

private val log = KotlinLogging.logger {}

@Component
class SecuritiesClientService(
    @Qualifier("happyEndingClient")
    private val webClient: WebClient
) {

    fun <U : Any> callHttpByGet(
        serviceType: ServiceType,
        bookId: Long,
        responseType: ParameterizedTypeReference<U>
    ): U {
        return webClient
            .get()
            .uri("/api/v1/books/$bookId")
            .accept(MediaType.TEXT_PLAIN)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response ->
                onStatus(response)
            }
            .bodyToFlux(responseType)
            .retryWhen(CustomRetry.create(serviceType))
            .onErrorMap { error -> SecuritiesCommon.onErrorMap(error) }
            .blockFirst()
            ?.also {
                log.info { "Response: $it" }
            } ?: throw SecuritiesException.of(SecuritiesErrorCode.LEDGER_RESPONSE_DATA_NULL)
    }

    private fun onStatus(
        response: ClientResponse
    ) = response.bodyToMono(String::class.java).map { responseMessage ->
        log.info {
            """
                |status_code: ${response.statusCode()}
                |response: $responseMessage
            """.trimMargin()
        }
        runCatching {
            if (response.statusCode().is4xxClientError) {
                SecuritiesException.of(
                    httpStatus = HttpStatus.BAD_REQUEST,
                    message = "잘못된 요청을 하셨습니다."
                )
            } else {
                SecuritiesException.of(
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                    message = "현재 서버에 접근할 수 없습니다."
                )
            }
        }.recover {
            SecuritiesException.of(
                securitiesErrorCode = SecuritiesErrorCode.LEDGER_SERVER_ERROR,
                cause = it,
                message = "현재 서버에 접근할 수 없습니다."
            )
        }.getOrThrow()
    }
}
