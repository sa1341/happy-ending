package com.kakaopaysec.happyending.global.error

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val status: HttpStatus,
    val message: String?
)
