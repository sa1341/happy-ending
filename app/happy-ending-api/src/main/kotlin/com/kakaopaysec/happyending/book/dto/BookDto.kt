package com.kakaopaysec.happyending.book.dto

import com.kakaopaysec.happyending.book.model.Book

data class BookDto(
    val bookId: Long,
    val titleKorean: String,
    val titleEnglish: String,
    val description: String,
    val author: String,
    val isbn: String,
    val publishDate: String,
    val createdAt: String,
    val modifiedAt: String,
) {
    companion object {
        fun from(book: Book): BookDto {
            return BookDto(
                bookId = book.bookId,
                titleKorean = book.titleKorean,
                titleEnglish = book.titleEnglish,
                description = book.description,
                author = book.author,
                isbn = book.isbn,
                publishDate = book.publishDate,
                createdAt = book.createdAt,
                modifiedAt = book.modifiedAt
            )
        }
    }
}
