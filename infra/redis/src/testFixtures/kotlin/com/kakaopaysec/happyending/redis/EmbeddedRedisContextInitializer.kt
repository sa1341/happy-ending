package com.kakaopaysec.happyending.redis

import com.kakaopaysec.happyending.redis.utils.TestSocketUtils
import mu.KotlinLogging
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent
import redis.embedded.RedisServer

const val EMBEDDED_REDIS_BEAN_NAME = "redisServer"

private val log = KotlinLogging.logger {}

class EmbeddedRedisContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val redisServer = RedisServer
            .builder()
            .port(TestSocketUtils.findAvailableTcpPort())
            .setting("maxmemory 256M").build()
            ?: RedisServer(63790)

        if (redisServer.isActive) {
            redisServer.stop()
        }

        redisServer.start()

        applicationContext.beanFactory.registerSingleton(EMBEDDED_REDIS_BEAN_NAME, redisServer)

        applicationContext.addApplicationListener {
            if (it is ContextClosedEvent) {
                redisServer.stop()
            }
        }

        val ports = redisServer.ports()
        log.info("Redis server started : $ports")
        if (ports.isEmpty()) throw RuntimeException("no redis ports is allocated")

        TestPropertyValues.of(
            "spring.data.redis.port=${ports[0]}"
        ).applyTo(applicationContext)
    }
}
