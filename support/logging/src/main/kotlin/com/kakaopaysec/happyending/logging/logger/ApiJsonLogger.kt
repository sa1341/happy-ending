package com.kakaopaysec.happyending.logging.logger

import com.kakaopaysec.happyending.logging.servlet.OutputStreamCopiedHttpResponseWrapper
import com.kakaopaysec.happyending.utils.DateTimeUtils.yyyyMMddTHHmmssSSSBar
import com.kakaopaysec.happyending.utils.ProfileUtils
import jakarta.servlet.http.HttpServletRequestWrapper
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import java.net.InetAddress
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val logger = KotlinLogging.logger("app-json-logger")

@Component
class ApiJsonLogger(
    private val profileUtils: ProfileUtils,
    @Value("\${spring.application.name}")
    private val appName: String
) {

     class LogFormat(
        val requestTime: String,
        val hostname: String,
        val port: Int,
        val method: String,
        val url: String,
        val phase: String,
        val appName: String,
        val request: Request<*>,
        val response: Response<*>,
        val elapsedTime: Long,
        val logType: JsonLogType = JsonLogType.INGRESS
    )

     class Request<T>(
        val headers: Map<String, String>,
        val body: T?
    )

     class Response<T>(
        val status: Int,
        val headers: Map<String, String>,
        val body: T?
    )

    fun info(
        startTime: LocalDateTime,
        request: ClientRequest,
        requestBody: String,
        response: ClientResponse?,
        responseBody: String
    ) {
        val logFormat = LogFormat(
            hostname = request.url().host,
            port = request.url().port,
            method = request.method().name(),
            phase = profileUtils.getActiveProfile().name,
            url = request.url().toString(),
            appName = appName,
            elapsedTime = ChronoUnit.MILLIS.between(startTime, LocalDateTime.now()),
            requestTime = startTime.format(yyyyMMddTHHmmssSSSBar),
            request = Request(
                headers = LoggingHelper.getHeadersFrom(request),
                body = LoggingHelper.maskBody(requestBody)
            ),
            response = Response(
                status = response?.statusCode()?.value() ?: 0,
                headers = response?.let {
                    LoggingHelper.getHeadersFrom(it)
                } ?: emptyMap(),
                body = LoggingHelper.maskBody(responseBody)
            ),
            logType = JsonLogType.EGRESS
        )
        logger.error { JsonHelper.convertToJson(logFormat) }
    }

    fun error(
        startTime: LocalDateTime,
        request: ClientRequest,
        requestBody: String,
        errorMessage: String
    ) {
        val logFormat = LogFormat(
            hostname = request.url().host,
            port = request.url().port,
            method = request.method().name(),
            phase = profileUtils.getActiveProfile().name,
            url = request.url().toString(),
            appName = appName,
            elapsedTime = ChronoUnit.MILLIS.between(startTime, LocalDateTime.now()),
            requestTime = startTime.format(yyyyMMddTHHmmssSSSBar),
            request = Request(
                headers = LoggingHelper.getHeadersFrom(request),
                body = LoggingHelper.maskBody(requestBody)
            ),
            response = Response(
                status = 0,
                headers = emptyMap(),
                body = errorMessage
            ),
            logType = JsonLogType.EGRESS
        )
        logger.error { JsonHelper.convertToJson(logFormat) }
    }

    fun info(
        startTime: LocalDateTime,
        request: HttpServletRequestWrapper,
        response: OutputStreamCopiedHttpResponseWrapper
    ) {
        val url = if (request.queryString == null) {
            request.requestURI
        } else {
            "${request.requestURI}?${request.queryString}"
        }
        val logFormat = LogFormat(
            hostname = InetAddress.getLocalHost().hostName,
            port = request.localPort,
            method = request.method,
            phase = profileUtils.getActiveProfile().name,
            url = url,
            appName = appName,
            elapsedTime = ChronoUnit.MILLIS.between(startTime, LocalDateTime.now()),
            requestTime = startTime.format(yyyyMMddTHHmmssSSSBar),
            request = Request(
                headers = LoggingHelper.getHeadersFrom(request),
                body = LoggingHelper.getBodyFrom(request)
            ),
            response = Response(
                status = response.status,
                headers = LoggingHelper.getHeadersFrom(response),
                body = LoggingHelper.getBodyFrom(response)
            )
        )
        logger.error { JsonHelper.convertToJson(logFormat) }
    }
}

enum class JsonLogType { INGRESS, EGRESS }
