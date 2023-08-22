package com.kakaopaysec.happyending.testkit.annotation

import org.junit.jupiter.api.Tag

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Tag("unit")
annotation class Unit
