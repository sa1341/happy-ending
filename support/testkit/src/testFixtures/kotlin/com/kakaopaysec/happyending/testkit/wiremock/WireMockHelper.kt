package com.kakaopaysec.happyending.testkit.wiremock

import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.extension.RegisterExtension

@WireMockTest
abstract class WireMockHelper {

    companion object {
        @JvmStatic
        @RegisterExtension
        val wm = WireMockExtension.newInstance()
            .options(WireMockConfiguration().dynamicPort())
            .build()
    }
}
