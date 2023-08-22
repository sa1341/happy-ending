package com.kakaopaysec.happyending.redis.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.kakaopaysec.happyending.utils.ProfileUtils
import io.lettuce.core.ClientOptions
import io.lettuce.core.ReadFrom
import io.lettuce.core.SocketOptions
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

private val log = KotlinLogging.logger {}

@Configuration
@EnableCaching
class RedisConfiguration(
    private val profileUtils: ProfileUtils,
    @Qualifier("redisObjectMapper")
    private val redisObjectMapper: ObjectMapper,
    private val redisProperties: RedisProperties,
    @Value("\${spring.redis.password:#{null}}")
    private val password: String?
) {
    @Bean
    @Primary
    internal fun redisConnectionFactory(): LettuceConnectionFactory {
        val profile = profileUtils.getActiveProfile()

        val redisPassword = password?.let {
            RedisPassword.of(it)
        } ?: RedisPassword.none()

        val options = ClientOptions.builder()
            .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
            .autoReconnect(true)
            .socketOptions(SocketOptions.builder().connectTimeout(redisProperties.connectTimeout).build())
            .build()

        val clientConfig = LettuceClientConfiguration.builder()
            .clientOptions(options)
            .commandTimeout(Duration.ofSeconds(3))
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .build()

        // TODO: 추후에 phase별로 sentinel 설정이 필요함
        val standAloneConfig = RedisStandaloneConfiguration()
            .apply {
                this.hostName = redisProperties.host
                this.port = redisProperties.port
                this.password = redisPassword
            }

        log.info("Redis connection information [phase : ${profile.name}] :: ${standAloneConfig.javaClass}")
        return LettuceConnectionFactory(standAloneConfig, clientConfig)
    }

    @Bean
    fun objectRedisTemplate(context: ApplicationContext): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>().apply {
            connectionFactory = redisConnectionFactory()
            keySerializer = StringRedisSerializer()
            valueSerializer = GenericJackson2JsonRedisSerializer(redisObjectMapper)
        }
        return redisTemplate
    }

    @Bean
    fun stringRedisTemplate(): StringRedisTemplate {
        return StringRedisTemplate(redisConnectionFactory())
    }

    @Bean
    @Primary
    fun redisCacheManager(context: ApplicationContext): CacheManager {
        val jsonSerializer = getJsonSerializer()
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(jsonSerializer)
            .computePrefixWith { cacheName ->
                "$CACHE_PREFIX${profileUtils.getActiveProfile().name}::$cacheName::"
            }
            .entryTtl(Duration.ofSeconds(60))
        return RedisCacheManager.builder(redisConnectionFactory())
            .cacheDefaults(configuration)
            .transactionAware()
            .build()
    }

    private fun redisCacheConfiguration(
        jsonSerializer: RedisSerializationContext.SerializationPair<Any>,
        serviceName: String,
        ttl: Duration
    ) = RedisCacheConfiguration.defaultCacheConfig()
        .serializeValuesWith(jsonSerializer)
        .computePrefixWith { cacheName ->
            "$CACHE_PREFIX${profileUtils.getActiveProfile().name}::$serviceName::$cacheName::"
        }
        .entryTtl(ttl)

    private fun getJsonSerializer(): RedisSerializationContext.SerializationPair<Any> {
        return RedisSerializationContext
            .SerializationPair
            .fromSerializer(GenericJackson2JsonRedisSerializer(redisObjectMapper))
    }

    companion object {
        val DAY_SECENDS: Long = 60 * 60 * 24
        val CACHE_PREFIX = "CACHE::PENSION::"
    }
}
