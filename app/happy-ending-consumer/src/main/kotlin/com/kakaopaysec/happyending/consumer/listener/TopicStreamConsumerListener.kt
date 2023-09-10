package com.kakaopaysec.happyending.consumer.listener

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

private val log = KotlinLogging.logger {}

@Configuration
class TopicStreamConsumerListener(
    private val kafkaJacksonMapper: ObjectMapper
) {

    @Bean
    fun topicConsumer() = Consumer<String> { value ->
        val person = kafkaJacksonMapper.readValue(value, Person::class.java)
        log.info { "result = $person" }
    }
}
