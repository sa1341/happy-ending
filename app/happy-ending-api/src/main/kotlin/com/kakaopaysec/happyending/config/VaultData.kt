package com.kakaopaysec.happyending.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger {}

@Configuration
data class VaultData(
    @Value("\${username}")
    val username: String,
    @Value("\${password}")
    val password: String
) {
    init {
        log.info { "$this" }
    }
}
