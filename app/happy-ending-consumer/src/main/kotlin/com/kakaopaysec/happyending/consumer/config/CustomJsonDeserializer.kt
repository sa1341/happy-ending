package com.kakaopaysec.happyending.consumer.config

import com.kakaopaysec.happyending.consumer.model.ChargeEvent
import org.apache.kafka.common.serialization.Deserializer

class CustomJsonDeserializer : Deserializer<ChargeEvent> {

    private val kafkaJacksonMapper = KafkaJacksonConfig().kafkaJacksonMapper()

    override fun deserialize(topic: String, data: ByteArray): ChargeEvent? {
        return runCatching {
            kafkaJacksonMapper.readValue(data, ChargeEvent::class.java)
        }.getOrNull()
    }

    override fun close() {
    }
}
