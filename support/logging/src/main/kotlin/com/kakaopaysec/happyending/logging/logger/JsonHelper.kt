package com.kakaopaysec.happyending.logging.logger

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.kakaopaysec.happyending.logging.logger.ApiJsonLogger.LogFormat

object JsonHelper {

    private val MAPPER = ObjectMapper()

    init {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        MAPPER.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
    }
    fun convertStringToJsonNode(plainText: String?): JsonNode? {
        return MAPPER.readTree(plainText)
    }

    fun getStringFromObject(obj: Any?): String? {
        return MAPPER.writeValueAsString(obj)
    }

    fun convertToJson(format: LogFormat): String =
        MAPPER.writeValueAsString(format)
}
