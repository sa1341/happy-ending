package com.kakaopaysec.happyending.oas

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ActiveProfiles("test")
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@SpringBootTest
class OasSampleApiTest {

    private lateinit var mockMvc: MockMvc

    private lateinit var spec: RequestSpecification

    @BeforeEach
    internal fun setUp(context: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    fun getSampleByIdTest() {
        val sampleId = "aaa"
        mockMvc.perform(
            get("/api/v1/samples/{sampleId}", sampleId)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("sample_id", `is`(sampleId)))
            .andExpect(jsonPath("name", `is`("sample-$sampleId")))
            .andDo(
                MockMvcRestDocumentationWrapper.document(
                    identifier = "sample",
                    resourceDetails = ResourceSnippetParametersBuilder()
                        .tag("Sample")
                        .description("Get a sample by id")
                        .pathParameters(
                            parameterWithName("sampleId").description("the sample id")
                        )
                        .responseFields(
                            fieldWithPath("sample_id").type(JsonFieldType.STRING).description("The sample identifier."),
                            fieldWithPath("name").type(JsonFieldType.STRING).description("The name of sample.")
                        )
                )
            )
    }
}
