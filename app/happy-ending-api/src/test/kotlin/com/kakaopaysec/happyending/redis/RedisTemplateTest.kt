package com.kakaopaysec.happyending.redis

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("local")
@SpringBootTest
class RedisTemplateTest @Autowired constructor(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val stringRedisTemplate: StringRedisTemplate
) {

    @Test
    fun `Redis 문자열을 저장한다`() {
        // given
        val string = stringRedisTemplate.opsForValue()
        val userId = "sa1341"
        // when
        string.set("user:$userId", userId)
        val actual = string.get("user:$userId")

        // then
        actual shouldBe userId
    }
}
