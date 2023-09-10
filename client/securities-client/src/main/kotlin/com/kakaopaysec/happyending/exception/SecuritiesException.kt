package com.kakaopaysec.happyending.exception

import org.springframework.http.HttpStatus

abstract class HappyEndingException(
    open val msg: String,
    open val httpStatus: HttpStatus,
    open val exceptionMessage: String,
    open val throwable: Throwable? = null
) : RuntimeException(exceptionMessage, throwable)

data class SecuritiesException(
    override val msg: String,
    override val httpStatus: HttpStatus,
    override val throwable: Throwable? = null,
    val description: String? = null
) : HappyEndingException(
    msg,
    httpStatus,
    """
        |[Message] : $msg
        |[description] : $description
    """.trimMargin(),
    throwable
) {

    override fun fillInStackTrace(): Throwable {
        return this
    }

    companion object {
        fun of(
            httpStatus: HttpStatus,
            message: String
        ) = SecuritiesException(
            msg = message,
            httpStatus = httpStatus
        )

        fun of(
            securitiesErrorCode: SecuritiesErrorCode
        ) = SecuritiesException(
            msg = securitiesErrorCode.errorMessage,
            httpStatus = securitiesErrorCode.errorStatus
        )

        fun of(
            securitiesErrorCode: SecuritiesErrorCode,
            cause: Throwable? = null,
            message: String? = null
        ): SecuritiesException {
            return SecuritiesException(
                msg = message ?: securitiesErrorCode.errorMessage,
                httpStatus = securitiesErrorCode.errorStatus,
                throwable = cause
            )
        }
    }
}

enum class SecuritiesErrorCode(
    val errorStatus: HttpStatus,
    val errorMessage: String
) {
    LEDGER_TIME_OUT(HttpStatus.GATEWAY_TIMEOUT, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (PL504)"),
    LEDGER_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (PL503)"),
    LEDGER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (PL500)"),
    LEDGER_BAD_PARAMETER(HttpStatus.INTERNAL_SERVER_ERROR, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (PL400)"),
    LEDGER_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (PL400)"),
    LEDGER_RESPONSE_DATA_NULL(HttpStatus.INTERNAL_SERVER_ERROR, "연결 상태가 좋지 않습니다. 조금 뒤 다시 시도해주세요. (PL400)")
}
