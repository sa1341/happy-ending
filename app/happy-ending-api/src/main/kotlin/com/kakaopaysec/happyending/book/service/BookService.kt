package com.kakaopaysec.happyending.book.service

import com.kakaopaysec.happyending.book.BookDomainService
import com.kakaopaysec.happyending.book.api.BookPublish
import com.kakaopaysec.happyending.book.dto.BookDto
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class BookService(
    private val bookDomainService: BookDomainService,
) {

    fun getBook(bookId: Long): BookDto? {
        val book = bookDomainService.getBook(bookId)
        return BookDto.from(book)
    }

    fun publishBook(bookPublish: BookPublish) {
        bookDomainService.publishBook(
            bookPublish = com.kakaopaysec.happyending.service.BookPublish(
                name = bookPublish.name,
                age = bookPublish.age
            )
        )
    }
}
