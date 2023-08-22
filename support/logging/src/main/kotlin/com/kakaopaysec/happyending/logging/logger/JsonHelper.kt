package com.kakaopaysec.happyending.logging.log.logger

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies

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
}
