package com.kakaopaysec.happyending.withdrawal.exception

open class AccountException(
    open val code: String,
    open val errorCode: AccountErrorCode,
    open val exceptionMessage: String,
    open val detail: Any? = null,
    override val cause: Throwable? = null,
) : RuntimeException(
    (
        """
    [ErrorCode] : $errorCode
    [ErrorMessage] : $exceptionMessage
    """ + if (detail != null) "[Details] : $detail" else ""
        ).trimIndent(),
    cause
)

class AccountBadRequestException(
    override val code: String,
    override val errorCode: AccountInputErrorCode,
    override val detail: Any? = null,
) : AccountException(
    code = code,
    errorCode = errorCode,
    exceptionMessage = errorCode.message,
    detail = detail
)

class AccountNicknameException(
    override val code: String,
    override val errorCode: AccountErrorCode,
    override val exceptionMessage: String,
    override val cause: Throwable? = null,
    override val detail: Any? = null,
) : AccountException(code, errorCode, exceptionMessage, detail, cause)
class EvaluationException(
    override val code: String,
    override val errorCode: EvaluationErrorCode,
    override val cause: Throwable? = null,
    override val detail: Any? = null,
) : AccountException(code, errorCode, errorCode.message, detail, cause)
