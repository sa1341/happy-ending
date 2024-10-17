package com.kakaopaysec.happyending.consumer.config

import com.kakaopaysec.happyending.consumer.listener.Person
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class TopicConsumerConfig(
    @Value("\${spring.kafka.bootstrap-servers:localhost:9092,localhost:9093,localhost:9094}")
    private val bootstrapServers: String,
) {

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Person> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Person>()
        factory.consumerFactory = DefaultKafkaConsumerFactory(consumerConfiguration())
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        return factory
    }

    @Bean
    fun consumerConfiguration(): Map<String, Any> {
        return hashMapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
            JsonDeserializer.VALUE_DEFAULT_TYPE to Person::class.java,
            JsonDeserializer.TRUSTED_PACKAGES to "*"
        )
    }
}
