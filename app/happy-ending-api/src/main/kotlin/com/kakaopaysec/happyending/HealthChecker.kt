package com.kakaopaysec.happyending

import com.kakaopaysec.happyending.account.api.SecretDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1")
@RestController
class HealthChecker(
    private val secretManagerConfig: SecretManagerConfig
) {

    @GetMapping
    fun ping() = "pong"

    @GetMapping("/secret-test")
    fun getSecretInfo(): SecretDto {
        return SecretDto(
            id = secretManagerConfig.id,
            password = secretManagerConfig.password
        )
    }
}
