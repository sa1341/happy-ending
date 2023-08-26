package com.kakaopaysec.happyending.logging.logger

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.kakaopaysec.happyending.logging.logback.MaskingPatterns
import com.kakaopaysec.happyending.logging.servlet.OutputStreamCopiedHttpResponseWrapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponseWrapper
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import java.io.InputStream

object LoggingHelper {

    fun getHeadersFrom(request: HttpServletRequestWrapper): Map<String, String> {
        return request.headerNames.toList().associateWith { key -> request.getHeader(key) }
    }

    fun getHeadersFrom(response: HttpServletResponseWrapper): Map<String, String> {
        return response.headerNames.toList().associateWith { key -> response.getHeader(key) }
    }

    fun getParametersFrom(request: HttpServletRequestWrapper): Map<String, String> {
        return request.parameterNames.toList().associateWith { key -> request.getParameter(key) }
    }

    fun getHeadersFrom(request: ClientRequest) = with(request.headers()) {
        keys.associateWith { key -> this[key]?.lastOrNull() ?: "" }
    }

    fun getHeadersFrom(response: ClientResponse) = with(response.headers().asHttpHeaders()) {
        keys.associateWith { key -> this[key]?.lastOrNull() ?: "" }
    }

    fun getBodyFrom(request: HttpServletRequest): String? {
        val requestBody = getPlainBodyFrom(request.inputStream)
        if (requestBody.isEmpty()) {
            return null
        }
        val contentType = request.contentType
        if (contentType != null && contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            return null
        }

        return maskBody(requestBody)
    }

    fun maskBody(body: String) = JsonHelper.runCatching {
        convertStringToJsonNode(body)?.let {
            for (key in MaskingPatterns.maskKeyList) {
                change(it, key, "*")
            }
            getStringFromObject(it)
        }
    }.getOrNull()

    fun getBodyFrom(response: OutputStreamCopiedHttpResponseWrapper): String? {
        return maskBody(String(response.copy, Charsets.UTF_8))
    }

    private fun getPlainBodyFrom(inputStream: InputStream): String =
        inputStream.bufferedReader(Charsets.UTF_8).readText()

    private fun change(parent: JsonNode, fieldName: String, newValue: String) {
        if (parent.has(fieldName)) {
            (parent as ObjectNode).put(fieldName, newValue)
        }

        for (child in parent) {
            change(child, fieldName, newValue)
        }
    }
}
