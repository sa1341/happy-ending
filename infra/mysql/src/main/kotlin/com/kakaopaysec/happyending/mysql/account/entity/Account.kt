package com.kakaopaysec.happyending.mysql.account.entity

import com.kakaopaysec.happyending.mysql.BaseAuditEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Table(name = "account")
@DynamicUpdate
@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    var id: Long? = null,

    @Column(name = "account_no")
    val accountNo: String,

    @Column(name = "name")
    val name: String,
) : BaseAuditEntity()
