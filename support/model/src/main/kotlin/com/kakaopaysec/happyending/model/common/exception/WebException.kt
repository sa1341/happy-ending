package com.kakaopaysec.happyending.model.common.exception

import org.springframework.http.HttpStatus

abstract class WebException(
    open val code: String,
    open val errorMessage: String,
    open val errorCode: ErrorCode,
    open val appUserId: String? = null,
    open val details: Any? = null,
    override val cause: Throwable? = null
) : RuntimeException(
    """
     [ErrorCode] : $errorCode
     [ErrorMessage] : $errorMessage
     [Details] : $details
    """.trimIndent(),
    cause
)

interface ErrorCode {
    val status: HttpStatus
    val message: String
}

enum class ServerErrorCode(
    override val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    override val message: String
) : ErrorCode {
    REDIS_SET_FAILED(message = "Redis에 데이터를 저장할 수 없습니다."),
    UNKNOWN_SERVER_ERROR(message = "알 수 없는 오류가 발생했습니다.")
}
