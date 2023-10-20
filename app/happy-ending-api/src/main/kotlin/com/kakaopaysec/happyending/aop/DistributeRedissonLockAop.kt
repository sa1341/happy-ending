package com.kakaopaysec.happyending.aop

import com.kakaopaysec.happyending.annotation.DistributeRedissonLock
import com.kakaopaysec.happyending.investment.model.InvestmentProductRequest
import com.kakaopaysec.happyending.investment.model.InvestmentProductResponse
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

    @Around("distributeRedissonTarget() && args(accountNumber, request)")
    @Throws(Throwable::class)
    fun distributeRedissonLock(joinPoint: ProceedingJoinPoint, accountNumber: String, request: InvestmentProductRequest): Any? {
        log.info("distributed Redisson In")
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val redissonLock = method.getAnnotation(DistributeRedissonLock::class.java)
        val key = "${request.fundCode}-$accountNumber"
        val rLock = redissonClient.getLock(key)

        return runCatching {
            val available = rLock.tryLock(redissonLock.waitTime, redissonLock.leaseTime, redissonLock.timeUnit)

            if (!available) {
                log.info { "Lock 획득 실패" }
                return InvestmentProductResponse(id = null, isSuccess = false)
            }
            return redissonTransaction.proceed(joinPoint)
        }.also {
            rLock.unlock()
        }.onFailure {
            throw it
        }
    }
}
