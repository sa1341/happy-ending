package com.kakaopaysec.happyending

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.kakaopaysec.happyending"])
open class HappyEndingApiApplication

fun main(args: Array<String>) {
    runApplication<HappyEndingApiApplication>(*args)
}
