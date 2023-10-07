package com.kakaopaysec.happyending.consumer.listener

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.kstream.Produced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.function.Consumer

private val log = KotlinLogging.logger {}

@Configuration
class TopicStreamConsumerListener(
    private val kafkaJacksonMapper: ObjectMapper
) {
    // spring cloud stream 기본 컨슈밍을 위한 의사코드 입니다. 실제로 사용 시에는 @Bean 등록이 필요함.
    fun topicConsumer() = Consumer<String> { value ->
        val person = kafkaJacksonMapper.readValue(value, Person::class.java)
        log.info { "result = $person" }
    }

    @Bean
    fun kStream(streamsBuilder: StreamsBuilder): KStream<String, Person> {
        val jsonSerdes = createJsonSerdes()
        val stream = streamsBuilder.stream(
            "stream-topic",
            Consumed.with(Serdes.String(), Serdes.serdeFrom(jsonSerdes.first, jsonSerdes.second))
        )

        stream.print(Printed.toSysOut())
        stream.mapValues(Person::name)
            .to(
                "out-topic",
                Produced.with(Serdes.String(), Serdes.String())
            )

        return stream
    }

    private fun createJsonSerdes(): Pair<JsonSerializer<Person>, JsonDeserializer<Person>> {
        val jsonSerializer = JsonSerializer<Person>()
        val jsonDeSerializer = JsonDeserializer(Person::class.java)
        jsonDeSerializer.setRemoveTypeHeaders(false)
        jsonDeSerializer.addTrustedPackages("*")
        jsonDeSerializer.setUseTypeMapperForKey(true)
        return Pair(jsonSerializer, jsonDeSerializer)
    }
}
