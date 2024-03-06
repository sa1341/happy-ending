package com.kakaopaysec.happyending

import mu.KotlinLogging
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import kotlin.system.exitProcess

private val log = KotlinLogging.logger {}

@SpringBootApplication
class HappyEndingBatchApplication

fun main(args: Array<String>) {
    try {
        exitProcess(
            SpringApplication.exit(
                SpringApplicationBuilder(HappyEndingBatchApplication::class.java)
                    .web(WebApplicationType.NONE)
                    .run(*args)
            )
        )
    } catch (e: Throwable) {
        log.error("Spring Batch execution failed", e)
        exitProcess(1)
    }
}
