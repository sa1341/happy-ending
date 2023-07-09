package com.kakaopaysec.happyending.book.api

import com.kakaopaysec.happyending.book.dto.BookDto
import com.kakaopaysec.happyending.book.service.BookService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RequestMapping("/api/v1/books")
@RestController
class BookApi(
    private val bookService: BookService
) {

    @GetMapping("/{bookId}")
    fun getBook(@PathVariable("bookId") bookId: Long): ResponseEntity<BookDto?> {
        log.debug { "bookId: $bookId" }
        val result = bookService.getBook(bookId)
        return ResponseEntity.ok().body(result)
    }
}
