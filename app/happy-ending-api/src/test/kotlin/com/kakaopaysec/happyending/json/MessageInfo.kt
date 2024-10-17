package com.kakaopaysec.happyending.json

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = TextMessage::class, name = "TEXT"),
    JsonSubTypes.Type(value = FileMessage::class, name = "FILE")
)
abstract class Message(
    open val messageId: Long,
    val type: MessageType,
)

@JsonTypeName(value = "TEXT")
data class TextMessage(
    override val messageId: Long,
    val message: String,
    val fundRatioInfoRequests: List<FundRatioInfoRequest>,
) : Message(messageId, MessageType.TEXT)

@JsonTypeName(value = "FILE")
data class FileMessage(
    override val messageId: Long,
    val fileInfo: String,
) : Message(messageId, MessageType.FILE)

enum class MessageType {
    TEXT, FILE
}

data class FundRatioInfoRequest(
    val ratio: BigDecimal,
    val itemCode: String,
    val fundSequence: Int,
)

class MessageSerializeTest {

    private val mapper = ObjectMapper().registerKotlinModule()

    @Test
    fun `JsonTypeInfo를 활용하여 구현클래스를 상위 클래스로 deserialize를 한다`() {
        // given
        val jsonString = "{\"type\":\"TEXT\",\"messageId\":1,\"message\":\"jeancalm\",\"fundRatioInfoRequests\":[{\"ratio\":500000,\"itemCode\":\"2000132\",\"fundSequence\":1},{\"ratio\":1000000,\"itemCode\":\"1500132\",\"fundSequence\":2}],\"type\":\"TEXT\"}" // ktlint-disable max-line-length

        val message = mapper.readValue(jsonString, Message::class.java)
        println(message)

        message should beInstanceOf<TextMessage>()

        val textMessage = message as TextMessage
        textMessage.fundRatioInfoRequests.forEach {
            println("requestInfo = $it")
        }
    }
}
