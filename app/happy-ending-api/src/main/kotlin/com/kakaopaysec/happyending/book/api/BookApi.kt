package com.kakaopaysec.happyending.book.api

import com.kakaopaysec.happyending.book.dto.BookDto
import com.kakaopaysec.happyending.book.service.BookService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

@RequestMapping("/api/v1/books")
@RestController
class BookApi(
    private val bookService: BookService
) {

    @PostMapping("/publication")
    fun getBookPublicationDate(@RequestBody bookPublication: BookPublication): BookPublication {
        log.debug { "BookPublication: $bookPublication" }
        return bookPublication
    }

    @PostMapping("/publication-book")
    fun publishBook(@RequestBody request: BookPublish) {
        log.debug { "BookPublication: $request" }
        bookService.publishBook(request)
    }

    @GetMapping("/{bookId}")
    fun getBook(@PathVariable("bookId") bookId: Long): ResponseEntity<BookDto?> {
        log.debug { "bookId: $bookId" }
        val result = bookService.getBook(bookId)
        return ResponseEntity.ok().body(result)
    }
}

data class BookPublish(
    val name: String,
    val age: Int
)

data class BookPublication(
    val name: String,
    val date: LocalDate,
    val dateTime: LocalDateTime
)
