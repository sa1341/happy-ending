package com.kakaopaysec.happyending.annotation

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributeRedissonLock(
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
    val waitTime: Long = 3L,
    val leaseTime: Long = 10L
)

