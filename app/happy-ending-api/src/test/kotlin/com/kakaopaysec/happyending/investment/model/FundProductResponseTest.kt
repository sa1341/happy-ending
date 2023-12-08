package com.kakaopaysec.happyending.investment.model

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.setExp
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class FundProductResponseTest {

    @Test
    fun sampleFundProductResponse() {
        // given
        val fixtureMonkey = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val fundProductResponse = fixtureMonkey.giveMeBuilder(FundProductResponse::class.java)
            .setExp(FundProductResponse::fundProductId, 1L)
            .sample()

        // then
        fundProductResponse.fundProductId shouldBe 1
        println("FundProductResponse = $fundProductResponse")
    }
}
