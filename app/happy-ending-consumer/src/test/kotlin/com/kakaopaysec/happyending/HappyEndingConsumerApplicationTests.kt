package com.kakaopaysec.happyending

import com.kakaopaysec.happyending.consumer.listener.Person
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class HappyEndingConsumerApplicationTests {

    @Test
    fun contextLoads() {
        val person = Person(
            name = "jeancalm",
            age = 32
        )
        val str = "{\"name\": \"jeancalm\", \"age\": 32}"
    }
}
