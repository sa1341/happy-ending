package com.kakaopaysec.happyending.utils

import com.kakaopaysec.happyending.utils.DateTimeUtils.yyyyMMddHHmmssSSS
import org.slf4j.MDC
import java.time.LocalDateTime
import java.util.UUID

const val SECURITIES_CHANNEL_VALUE = "SEC"

object GuidUtils {

    private const val TRANSACTION_ID = "transaction-id"

    fun get(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString().replace("-".toRegex(), "")
    }

    fun generateGuid(): String {
        val uuid = UUID.randomUUID().toStringRemoveDashes()
        return "$SECURITIES_CHANNEL_VALUE${LocalDateTime.now().yyyyMMddHHmmssSSS()}${
        uuid.substring(
            uuid.length - 12,
            uuid.length
        )
        }"
    }

    fun getTransactionId() = MDC.get(TRANSACTION_ID) ?: ""

    fun initGuidInfo(transactionId: String?) {
        val guid = transactionId?.let {
            it.split("-").lastOrNull() ?: ""
        } ?: generateGuid()

        MDC.put(TRANSACTION_ID, guid)
    }
}
