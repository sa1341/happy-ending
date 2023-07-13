package com.kakaopaysec.happyending.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class PrometheusPapiConfiguration(
    @Value("\${prometheus.actuator.username}")
    private val userName: String,
    @Value("\${prometheus.actuator.password}")
    private val password: String
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests { authCustomizer ->
                authCustomizer
                    .requestMatchers("/actuator/prometheus")
                    .hasRole("prometheus-user")
                    .requestMatchers("/**")
                    .permitAll()
            }
            .httpBasic(withDefaults())
            .build()
    }

    @Bean
    fun userDetailService(): UserDetailsService {
        return User.withUsername(userName)
            .password("{noop}$password")
            .roles("prometheus-user")
            .build().let {
                InMemoryUserDetailsManager(it)
            }
    }
}
