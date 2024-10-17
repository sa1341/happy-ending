package com.kakaopaysec.happyending.logging.filter

import com.kakaopaysec.happyending.logging.logger.ApiJsonLogger
import com.kakaopaysec.happyending.logging.logger.OperationLogger
import com.kakaopaysec.happyending.logging.servlet.BodyFetchedHttpRequestWrapper
import com.kakaopaysec.happyending.logging.servlet.OutputStreamCopiedHttpResponseWrapper
import com.kakaopaysec.happyending.utils.ProfileUtils
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

@Profile("!test")
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Component
class IOLoggingFilter(
    private val apiJsonLogger: ApiJsonLogger,
    private val operationLogger: OperationLogger,
    private val profileUtils: ProfileUtils,
) : Filter {

    override fun doFilter(
        servletRequest: ServletRequest,
        servletResponse: ServletResponse,
        chain: FilterChain,
    ) {
        val startTime = LocalDateTime.now()
        val contentType = servletRequest.contentType
        val httpServletRequest = servletRequest as HttpServletRequest

        val request =
            if (contentType != null && contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                ContentCachingRequestWrapper(httpServletRequest)
            } else {
                BodyFetchedHttpRequestWrapper(httpServletRequest)
            }

        val response = OutputStreamCopiedHttpResponseWrapper(servletResponse as HttpServletResponse)

        try {
            chain.doFilter(request, response)
            response.flushBuffer()
        } catch (e: Exception) {
            log.error(e) { "[IOLoggingFilter] Error handled: ${e.message ?: e.stackTraceToString()}" }
        } finally {
            if (isNeededLogUrl(request.requestURI)) {
                operationLogger.info(startTime, request, response)

                if (!profileUtils.isLocalProfile()) {
                    apiJsonLogger.info(startTime, request, response)
                }
            }
        }
    }

    private val noNeedLogList = listOf("/actuator", "/docs")

    private fun isNeededLogUrl(url: String): Boolean {
        return !noNeedLogList.any { url.startsWith(it) }
    }
}
