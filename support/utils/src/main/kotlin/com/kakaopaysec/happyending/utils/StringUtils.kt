package com.kakaopaysec.happyending.utils

import java.math.BigDecimal
import java.util.Base64

fun String?.toBigDecimalOrZero(): BigDecimal {
    if (this.isNullOrBlank()) {
        return BigDecimal.ZERO
    }

    return try {
        this.toBigDecimal()
    } catch (e: Exception) {
        BigDecimal.ZERO
    }
}

fun String.isBigDecimal() = this.toBigDecimalOrNull()?.let { true } ?: false

fun String.toBooleanFromYN(): Boolean? = when (this) {
    "Y", "y" -> true
    "N", "n" -> false
    else -> null
}

fun String.parseBoolean(): Boolean = when (this.uppercase()) {
    "Y" -> true
    "N" -> false
    else -> throw IllegalArgumentException("Text '$this' 값을 변환할 수 없습니다.")
}

fun Boolean.toYN(): String = if (this) "Y" else "N"

fun String.base64Decode(): String =
    String(Base64.getDecoder().decode(this))
