package com.kakaopaysec.happyending.consumer.listener

import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class TopicMessageListener {

    @KafkaListener(
        topics = ["topic"],
        groupId = "\${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun handle(record: ConsumerRecord<String, String>, ack: Acknowledgment) {
        log.info { "data = ${record.value()}" }

        ack.acknowledge()
    }
}

data class Person(
    val name: String,
    val age: Int
)
