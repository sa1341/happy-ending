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
    // spring cloud stream 기본 컨슈밍을 위한 의사코드 입니다. 실제로 사용 시에는 @Bean 등록이 필요함.
    @Bean
    fun topicConsumer() = Consumer<Person> { person ->
        if (person.name == "진카미") throw IllegalArgumentException("dlq test")
        log.info { "result = $person" }
    }
}

