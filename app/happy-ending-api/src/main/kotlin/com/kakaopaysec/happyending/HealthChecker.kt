package com.kakaopaysec.happyending

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1")
@RestController
class HealthChecker {

    @GetMapping
    fun ping() = "pong"
}
