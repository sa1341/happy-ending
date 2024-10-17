package com.kakaopaysec.happyending.common.error

import com.kakaopaysec.happyending.model.common.exception.ErrorCode
import org.springframework.http.HttpStatus
enum class SecuritiesErrorCode(
    override val status: HttpStatus,
    override val message: String,
) : ErrorCode {
    SECURITIES_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (500)"),
    SECURITIES_RESPONSE_DATA_NULL(HttpStatus.INTERNAL_SERVER_ERROR, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요, (400)"),
    SECURITIES_TIME_OUT(HttpStatus.GATEWAY_TIMEOUT, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (504)"),
    SECURITIES_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (503)"),
    SECURITIES_BAD_PARAMETER(HttpStatus.BAD_REQUEST, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (400)"),
    SECURITIES_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (400)"),
    ;
}
