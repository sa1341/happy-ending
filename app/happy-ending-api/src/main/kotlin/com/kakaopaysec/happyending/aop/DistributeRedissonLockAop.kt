package com.kakaopaysec.happyending.aop

import com.kakaopaysec.happyending.annotation.DistributeRedissonLock
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Aspect
@Component
class DistributeRedissonLockAop(
    private val redissonClient: RedissonClient,
    private val redissonTransaction: RedissonTransaction
) {
    @Pointcut("@annotation(com.kakaopaysec.happyending.annotation.DistributeRedissonLock)")
    fun distributeRedissonTarget() {}

/*    @Around("distributeRedissonTarget() && args(userId, request)")
    @Throws(Throwable::class)
    fun distributeRedissonLock(joinPoint: ProceedingJoinPoint, userId: String): Any? {
        log.info("distributed Redisson In")
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val redissonLock = method.getAnnotation(DistributeRedissonLock::class.java)
        val key = userId
        val rLock = redissonClient.getLock(key)

        return runCatching {
            val available = rLock.tryLock(redissonLock.waitTime, redissonLock.leaseTime, redissonLock.timeUnit)

            if (!available) {
                log.info { "Lock 획득 실패" }
            }
            return redissonTransaction.proceed(joinPoint)
        }.also {
            rLock.unlock()
        }.onFailure {
            throw it
        }
    }*/
}
