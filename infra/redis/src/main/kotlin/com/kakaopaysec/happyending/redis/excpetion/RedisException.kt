package com.kakaopaysec.happyending.redis.excpetion

import com.kakaopaysec.happyending.model.common.exception.ErrorCode
import com.kakaopaysec.happyending.model.common.exception.WebException
import org.springframework.http.HttpStatus

open class RedisException(
    override val errorCode: RedisErrorCode,
    override val errorMessage: String,
    open val detail: Any? = null,
    override val cause: Throwable? = null,
) : WebException(
    code = errorCode.name,
    errorCode = errorCode,
    errorMessage = errorCode.message,
    cause = cause
)

enum class RedisErrorCode(
    override val status: HttpStatus,
    override val message: String,
) : ErrorCode {
    WRONG_HEADER_PAY_ACCOUNT(HttpStatus.BAD_REQUEST, "x-pay-account 헤더가 올바르지 않습니다."),
    WRONG_HEADER_PAY_UUID(HttpStatus.BAD_REQUEST, "x-pay-uuid 헤더가 올바르지 않습니다."),
    WRONG_HEADER_USER_AGENT(HttpStatus.BAD_REQUEST, "x-user-agent 헤더가 올바르지 않습니다."),
    NO_QUERY_PARAM(HttpStatus.BAD_REQUEST, "쿼리 문자열 값을 찾을 수 없습니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 날짜 형식입니다."),
    NOT_ALLOWED_VALUE(HttpStatus.BAD_REQUEST, "허용하지 않는 값 입니다."),
    WEB_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    REQUEST_BODY_REQUIRED(HttpStatus.BAD_REQUEST, "Request Body가 필요한 요청입니다."),
    CERTIFICATE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증서가 존재하지 않습니다."),
    CERTIFICATE_VERIFICATION_FAILED(HttpStatus.CONFLICT, "서명값 검증에 실패했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, message = "요청한 리소스를 찾을 수 없습니다."),
}
