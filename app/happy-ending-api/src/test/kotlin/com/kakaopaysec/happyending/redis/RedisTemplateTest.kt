package com.kakaopaysec.happyending.redis

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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

    @Test
    fun `redis sorted set 타입을 저장 및 조회한다`() {
        // given
        val key = "userRank"
        val zSet = redisTemplate.opsForZSet()

        // when
        val expected = hashSetOf<Any>()
        expected.add("a")
        expected.add("b")
        expected.add("c")
        expected.add("d")
        expected.add("e")
        expected.add("f")

        zSet.add(key, "d", 4.toDouble())
        zSet.add(key, "c", 3.toDouble())
        zSet.add(key, "a", 1.toDouble())
        zSet.add(key, "b", 2.toDouble())
        zSet.add(key, "f", 6.toDouble())
        zSet.add(key, "e", 5.toDouble())
        val range = zSet.range(key, 0, 6)

        // then
        range shouldNotBe null
        range!!.size shouldBe 6
    }
}
