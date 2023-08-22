package com.kakaopaysec.happyending.redis.annotation

import com.kakaopaysec.happyending.redis.EmbeddedRedisContextInitializer
import org.springframework.test.context.ContextConfiguration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ContextConfiguration(
    initializers = [EmbeddedRedisContextInitializer::class]
)
annotation class EnableEmbeddedRedis()
