package com.kakaopaysec.log.logback

import org.apache.commons.lang3.RegExUtils
import java.util.regex.Pattern
import java.util.stream.Collectors

internal object MaskingPatterns {
    private const val PATTERN_TO_MASK = "(?<=\"?{0}\"?\\s?[:|=]\\s?\"?)[0-9|a-z|A-Z|가-힣|ㄱ-ㅎ|.@_-]+"
    private val maskKeyList =
        listOf("email", "account_number", "accountNumber", "phone_number", "phoneNumber", "nickname", "acno")

    private const val MASKING_STRING = "***"

    val maskPatterns = ArrayList<String>().apply {
        clear()
        maskKeyList.forEach {
            add(PATTERN_TO_MASK.replaceFirst("{0}", it))
        }
    }

    private val multilinePattern =
        Pattern.compile(
            maskPatterns.stream().collect(Collectors.joining("|")),
            Pattern.MULTILINE or Pattern.CASE_INSENSITIVE
        )

    fun maskMessage(message: String) = multilinePattern?.let {
        RegExUtils.replaceAll(message, it, MASKING_STRING)
    } ?: message
}
