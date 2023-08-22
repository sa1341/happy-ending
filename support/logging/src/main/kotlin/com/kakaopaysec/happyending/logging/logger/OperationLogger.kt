package com.kakaopaysec.happyending.logging.log.logger

import com.kakaopaysec.happyending.logging.log.servlet.OutputStreamCopiedHttpResponseWrapper
import com.kakaopaysec.happyending.logging.logger.LoggingHelper
import jakarta.servlet.http.HttpServletRequestWrapper
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val logger = KotlinLogging.logger {}

@Component
class OperationLogger {
    fun info(
        startTime: LocalDateTime,
        request: HttpServletRequestWrapper,
        response: OutputStreamCopiedHttpResponseWrapper
    ) {
        try {
            val elapsedTime = ChronoUnit.MILLIS.between(startTime, LocalDateTime.now())
            if (elapsedTime >= ELAPSED_TIME_THRESHOLD) {
                logger.error(
                    ERROR_LOGGING_FORMAT,
                    response.status,
                    elapsedTime,
                    request.method,
                    request.requestURI,
                    startTime,
                    LoggingHelper.getBodyFrom(request),
                    LoggingHelper.getBodyFrom(response)
                )
            }
            logger.info(
                LOGGING_FORMAT,
                response.status,
                elapsedTime,
                request.method,
                request.requestURI,
                request.remoteAddr,
                startTime,
                LoggingHelper.getHeadersFrom(request),
                LoggingHelper.getParametersFrom(request),
                LoggingHelper.getBodyFrom(request),
                LoggingHelper.getBodyFrom(response)
            )
        } catch (e: IOException) {
            logger.error("{}", e.message, e)
        }
    }

    companion object {
        const val ELAPSED_TIME_THRESHOLD = 5 * 1000

        const val LOGGING_FORMAT = """
> Completed {} in {}ms
> Started {} {} for {} at {}
> Headers : {}
> Params : {}
> Request body : {}
> Response body : {}
            """

        const val ERROR_LOGGING_FORMAT = """
> Completed {} in {}ms
> {} {} at {}
> Request body : {}
> Response body : {}
            """
    }
}
