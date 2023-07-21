package com.kakaopaysec.happyending.config

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Resilience4jConfiguration {

    companion object {
        const val SECURITIES = "securities"
    }

    @Bean
    fun securitiesCircuitBreaker(registry: CircuitBreakerRegistry) =
        registry.circuitBreaker(SECURITIES)
}
