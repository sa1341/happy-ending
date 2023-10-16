package com.kakaopaysec.happyending.consumer.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kakaopaysec.happyending.consumer.market.model.Purchase
import com.kakaopaysec.happyending.consumer.market.model.PurchasePattern
import com.kakaopaysec.happyending.consumer.market.model.RewardAccumulator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.springframework.kafka.support.serializer.JsonSerializer as KafkaJsonSerializer
import com.kakaopaysec.happyending.consumer.stock.model.StockTickerData

@Configuration
class KafkaJacksonConfig {
    @Bean
    @Primary
    fun kafkaJacksonMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .registerModule(Jdk8Module())
            .registerModule(customModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    }

    private fun customModule(): JavaTimeModule = JavaTimeModule().apply {
        addSerializer(
            LocalDate::class.java,
            LocalDateSerializer(datePattern)
        )
        addDeserializer(
            LocalDate::class.java,
            LocalDateDeserializer(datePattern)
        )
        addSerializer(
            LocalDateTime::class.java,
            LocalDateTimeSerializer(dateTimePattern)
        )
        addDeserializer(
            LocalDateTime::class.java,
            LocalDateTimeDeserializer(dateTimePattern)
        )
        addSerializer(
            BigDecimal::class.java,
            CustomBigDecimalSerializer()
        )
    }

    class CustomBigDecimalSerializer : JsonSerializer<BigDecimal>() {
        override fun serialize(value: BigDecimal, generator: JsonGenerator, serializerProvider: SerializerProvider) {
            generator.writeString(value.stripTrailingZeros().toPlainString())
        }
    }

    companion object {
        val datePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val dateTimePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")

        fun createPurchaseJsonSerdes(): Pair<KafkaJsonSerializer<Purchase>, JsonDeserializer<Purchase>> {
            val jsonSerializer = KafkaJsonSerializer<Purchase>()
            val jsonDeSerializer = JsonDeserializer(Purchase::class.java)
            jsonDeSerializer.setRemoveTypeHeaders(false)
            jsonDeSerializer.addTrustedPackages("*")
            jsonDeSerializer.setUseTypeMapperForKey(true)
            return Pair(jsonSerializer, jsonDeSerializer)
        }

        fun createPurchasePatternJsonSerdes(): Pair<KafkaJsonSerializer<PurchasePattern>, JsonDeserializer<PurchasePattern>> {
            val jsonSerializer = KafkaJsonSerializer<PurchasePattern>()
            val jsonDeSerializer = JsonDeserializer(PurchasePattern::class.java)
            jsonDeSerializer.setRemoveTypeHeaders(false)
            jsonDeSerializer.addTrustedPackages("*")
            jsonDeSerializer.setUseTypeMapperForKey(true)
            return Pair(jsonSerializer, jsonDeSerializer)
        }

        fun createRewardAccumulatorJsonSerdes(): Pair<KafkaJsonSerializer<RewardAccumulator>, JsonDeserializer<RewardAccumulator>> {
            val jsonSerializer = KafkaJsonSerializer<RewardAccumulator>()
            val jsonDeSerializer = JsonDeserializer(RewardAccumulator::class.java)
            jsonDeSerializer.setRemoveTypeHeaders(false)
            jsonDeSerializer.addTrustedPackages("*")
            jsonDeSerializer.setUseTypeMapperForKey(true)
            return Pair(jsonSerializer, jsonDeSerializer)
        }

        fun createStockTickerJsonSerdes(): Pair<KafkaJsonSerializer<StockTickerData>, JsonDeserializer<StockTickerData>> {
            val jsonSerializer = KafkaJsonSerializer<StockTickerData>()
            val jsonDeSerializer = JsonDeserializer(StockTickerData::class.java)
            jsonDeSerializer.setRemoveTypeHeaders(false)
            jsonDeSerializer.addTrustedPackages("*")
            jsonDeSerializer.setUseTypeMapperForKey(true)
            return Pair(jsonSerializer, jsonDeSerializer)
        }
    }
}
