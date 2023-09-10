package com.kakaopaysec.happyending.utils

import com.kakaopaysec.happyending.utils.DateTimeUtils.yyyyMMddHHmmssSSS
import java.time.LocalDateTime
import java.util.UUID

const val SECURITIES_CHANNEL_VALUE = "PAY"

object GuidUtils {
    fun get(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString().replace("-".toRegex(), "")
    }

    fun generateSecuritiesGuid(): String {
        val uuid = UUID.randomUUID().toStringRemoveDashes()
        return "$SECURITIES_CHANNEL_VALUE${LocalDateTime.now().yyyyMMddHHmmssSSS()}${
        uuid.substring(
            uuid.length - 12,
            uuid.length
        )
        }"
    }
}
