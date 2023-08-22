package com.kakaopaysec.happyending.mysql

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseAuditEntity {
    @Column(nullable = false, updatable = false)
    @CreatedDate
    open var createdAt: LocalDateTime = LocalDateTime.now()

    @CreatedBy
    open var createdBy: String = ""

    @LastModifiedDate
    open var updatedAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedBy
    open var updatedBy: String = ""
}
