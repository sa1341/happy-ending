package com.kakaopaysec.happyending.utils

import com.fasterxml.jackson.annotation.JsonValue
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

object ChannelTypeUtils {
    private fun getRequestChannel(): ChannelType? {
        val request = RequestContextHolder.getRequestAttributes()?.let {
            (it as ServletRequestAttributes).request
        }

        return request?.getHeader(Header.CHANNEL_TYPE)?.let {
            runCatching {
                ChannelType.valueOf(it.uppercase())
            }.getOrNull()
        }
    }

    fun toMediaCode(): MediaCode {
        return when (getRequestChannel()) {
            ChannelType.KAKAOTALK -> MediaCode.KAKAOPAY_INVEST
            ChannelType.ADMIN -> MediaCode.INTEGRATED_TERMINAL
            else -> MediaCode.KAKAOPAY_INVEST
        }
    }
}

enum class MediaCode(@JsonValue val code: String, val description: String) {
    INTEGRATED_TERMINAL("011", "통합단말(직원)"),
    ARS("100", "ARS"),
    KAKAOPAY_ACCOUNT("P10", "kakaopay 공통(고객, 계좌)"),
    KAKAOPAY_MONEY("P20", "kakaopay 머니(출납)"),
    KAKAOPAY_INVEST("P30", "kakaopay 투자(수익증권)"),
    KAKAOPAY_JARVIS("P40", "jarvis 주문"),
    KAKAOPAY_STOCK("P50", "kakaopay 주식"),
    KAKAO_TALK_PAY("T10", "kakao talk pay"),
    KAKAOPAY_APP("110", "페이앱")
}

enum class SystemCode(@JsonValue val code: String, val description: String) {
    DEVELOP("D", "pay 배포 기준: 없음"),
    TEST("T", "pay 배포 기준: dev, sandbox"),
    PRODUCT("P", "pay 배포 기준: beta, production"),
    LOCAL("L", "local"),
    SIMULATION("S", "시뮬레이션")
}

enum class ChannelType {
    PAYAPP,
    KAKAOTALK,
    ADMIN
}

object Header {
    const val APP_USER_ID = "x-app-user-id"
    const val CHANNEL_TYPE = "x-channel-type"
    const val CIF = "x-cif"
    const val TRANSACTION_ID = "x-transaction-id"
    const val REQUEST_ID = "x-request-id"
}
