package com.kakaopaysec.happyending.utils

import java.util.UUID

object GuidUtils {
    fun get(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString().replace("-".toRegex(), "")
    }
}
