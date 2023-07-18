
package com.kakaopaysec.happyending.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.kakaopaysec.happyending.config.JacksonConfiguration
import com.kakaopaysec.happyending.config.WebConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@ContextConfiguration(classes = [JacksonConfiguration::class, WebConfiguration::class])
@ActiveProfiles("test")
@EnableAutoConfiguration
open class RestDocUtils {

    @Autowired
    @Qualifier("happyEndingObjectMapper")
    protected lateinit var objectMapper: ObjectMapper
    protected lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentation: RestDocumentationContextProvider
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                    .operationPreprocessors()
                    .withRequestDefaults(Preprocessors.prettyPrint())
                    .withResponseDefaults(Preprocessors.prettyPrint())
            )
            .build()
    }

    fun MockHttpServletRequestBuilder.withPaySecAccount(): MockHttpServletRequestBuilder {
        return this
            .header(Header.APP_USER_ID, "94542")
            .contentType(MediaType.APPLICATION_JSON)
    }
}

object Header {
    const val APP_USER_ID = "x-app-user-id"
}
