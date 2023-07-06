package com.kakaopaysec.happyending

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SecretManagerConfig(
    @Value("\${test.id}")
    val id: String,
    @Value("\${test.password}")
    val password: String
)
