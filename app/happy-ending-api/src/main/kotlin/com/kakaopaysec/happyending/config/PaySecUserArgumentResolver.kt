package com.kakaopaysec.happyending.config

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class PaySecUserArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == PaySecUser::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): PaySecUser {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
        val appUserId = request?.getHeader("x-app-user-id") ?: throw RuntimeException("No x-app-user-id header")
        return PaySecUser(appUserId)
    }
}

data class PaySecUser(
    val appUserId: String
)
