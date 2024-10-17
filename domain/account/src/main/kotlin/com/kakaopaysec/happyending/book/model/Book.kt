package com.kakaopaysec.happyending.book.model

data class Book(
    val bookId: Long,
    val titleKorean: String,
    val titleEnglish: String,
    val description: String,
    val author: String,
    val isbn: String,
    val publishDate: String,
    val createdAt: String,
    val modifiedAt: String,
)
