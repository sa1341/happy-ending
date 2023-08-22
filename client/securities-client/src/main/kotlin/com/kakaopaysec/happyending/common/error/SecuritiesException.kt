package com.kakaopaysec.happyending.common.error

import com.kakaopaysec.happyending.common.model.SecuritiesClientMessage
import com.kakaopaysec.happyending.model.common.exception.WebException

class SecuritiesException(
    override val code: String,
    override val errorCode: SecuritiesErrorCode,
    override val appUserId: String? = null,
    override val cause: Throwable? = null,
    val customMessage: String? = null,
    val securitiesError: SecuritiesError? = null
) : WebException(
    code = code,
    errorCode = errorCode,
    errorMessage = "${customMessage ?: securitiesError?.messageContent}",
    appUserId = appUserId,
    cause = cause
) {

    override fun fillInStackTrace(): Throwable {
        return this
    }

    companion object {
        fun of(
            errorCode: SecuritiesErrorCode,
            customMessage: String? = null,
            securitiesClientMessage: SecuritiesClientMessage,
            appUserId: String?
        ) =
            SecuritiesException(
                code = securitiesClientMessage.messageCode,
                customMessage = customMessage,
                securitiesError = SecuritiesError.of(securitiesClientMessage),
                appUserId = appUserId,
                errorCode = errorCode
            )

        fun of(
            securitiesErrorCode: SecuritiesErrorCode,
            message: String? = null,
            appUserId: String? = null,
            cause: Throwable? = null
        ): SecuritiesException {
            return SecuritiesException(
                customMessage = message ?: securitiesErrorCode.message,
                code = securitiesErrorCode.name,
                appUserId = appUserId,
                errorCode = securitiesErrorCode,
                cause = cause
            )
        }
    }
}

data class SecuritiesError(
    val messageCode: String,
    val messageContent: String
) {
    companion object {
        fun of(message: SecuritiesClientMessage): SecuritiesError = SecuritiesError(
            messageCode = message.messageCode,
            messageContent = message.messageContent
        )
    }
}
