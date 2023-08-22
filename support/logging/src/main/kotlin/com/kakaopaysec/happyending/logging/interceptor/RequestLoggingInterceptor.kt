package com.kakaopaysec.happyending.logging.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.web.servlet.HandlerInterceptor

private val log = KotlinLogging.logger { }

class RequestLoggingInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        log.info {
            """REQUEST
                | Request URI : ${request.requestURI}
                | Request Method : ${request.method}
                | Request Header : ${request.headerNames.toList()
                .joinToString { "$it:${request.getHeader(it)}" }}
            """.trimMargin()
        }

        return true
    }
}
