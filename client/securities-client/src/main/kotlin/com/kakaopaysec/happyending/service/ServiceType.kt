package com.kakaopaysec.happyending.service

enum class ServiceType(
    val url: String,
    val isRetry: Boolean
) {
    GET_BOOK("/api/v1/books", true);
}
