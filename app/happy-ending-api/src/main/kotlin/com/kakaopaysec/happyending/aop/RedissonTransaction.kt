package com.kakaopaysec.happyending.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class RedissonTransaction {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun proceed(joinPoint: ProceedingJoinPoint): Any? {
        return joinPoint.proceed()
    }
}
