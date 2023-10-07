package com.kakaopaysec.happyending

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams

@EnableKafkaStreams @EnableKafka @SpringBootApplication
class HappyEndingConsumerApplication

fun main(args: Array<String>) {
    runApplication<HappyEndingConsumerApplication>(*args)
}
