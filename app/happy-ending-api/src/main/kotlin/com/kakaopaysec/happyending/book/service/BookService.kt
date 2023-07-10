package com.kakaopaysec.happyending.book.service

import com.kakaopaysec.happyending.book.dto.BookDto
import com.kakaopaysec.happyending.global.error.BookNotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient

private val log = KotlinLogging.logger {}

@Service
class BookService(
    private val webClient: WebClient
) {

    fun getBook(bookId: Long): BookDto? {
        return webClient
            .get()
            .uri("/api/v1/books/$bookId")
            .accept(MediaType.TEXT_PLAIN)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response ->
                log.error { "Status Code: ${response.statusCode()}" }
                onStatus(
                    response
                )
            }
            .bodyToFlux(BookDto::class.java)
            .onErrorMap {
                log.error { "Exception Type: $it ,error mapping" }
                BookNotFoundException()
            }
            .blockFirst()
            ?.also {
                log.info {
                    """
                    |request: $bookId"
                    |response: $it
                    """.trimIndent()
                }
            } ?: throw BookNotFoundException()
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
                BookNotFoundException()
            } else {
                BookNotFoundException()
            }
        }.recover {
            BookNotFoundException()
        }.getOrThrow()
    }
}
