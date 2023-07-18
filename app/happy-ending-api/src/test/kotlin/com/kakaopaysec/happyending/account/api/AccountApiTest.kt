package com.kakaopaysec.happyending.account.api

import com.kakaopaysec.happyending.account.service.AccountResponse
import com.kakaopaysec.happyending.account.service.AccountService
import com.kakaopaysec.happyending.common.RestDocUtils
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(
    classes = [AccountApi::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class AccountApiTest : RestDocUtils() {

    @MockkBean
    private lateinit var accountService: AccountService

    @Test
    fun `계좌번호를 조회한다`() {
        // given
        every {
            accountService.getAccountNumber(any(), any())
        } returns AccountResponse(appUserId = "209987", accountNumber = "02000162758")

        // when
        val result = mockMvc.perform(
            post("/v1/account/get")
                .withPaySecAccount()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        AccountNumber("02000162758")
                    )
                )
        ).andDo(MockMvcResultHandlers.print())

        result.andExpect(status().isOk)
            .andDo(
                document(
                    "account",
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("account_number").type(JsonFieldType.STRING)
                            .description("계좌번호"),
                        PayloadDocumentation.fieldWithPath("app_user_id").type(JsonFieldType.STRING)
                            .description("앱유저")
                    )
                )
            )
    }
}
