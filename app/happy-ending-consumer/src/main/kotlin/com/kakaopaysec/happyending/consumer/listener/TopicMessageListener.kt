package com.kakaopaysec.happyending.consumer.listener

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class TopicMessageListener(
    private val kafkaJacksonMapper: ObjectMapper
) {

    @KafkaListener(
        topics = ["basic-topic"],
        groupId = "happyEndingGroup",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun handle(record: ConsumerRecord<String, String>, ack: Acknowledgment) {
        log.info { record.value() }
        ack.acknowledge()
    }

    @KafkaListener(
        topics = ["topicConsumerFail"],
        groupId = "happyEndingGroup",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @DltHandler
    fun handleDlq(record: ConsumerRecord<String, Person>, ack: Acknowledgment) {
        log.error { "DLQ: ${record.value()}" }
        ack.acknowledge()
    }
}

data class Person(
    val name: String,
    val age: Int
)
