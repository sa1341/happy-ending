package com.kakaopaysec.happyending.mysql.fund.entity

import com.kakaopaysec.happyending.mysql.BaseAuditEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Table(name = "fund_product")
@DynamicUpdate
@Entity
class FundProductEntity(
    @Column(name = "code")
    val code: String,
    @Column(name = "name")
    val name: String
) : BaseAuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fund_product_id")
    var id: Long? = null
}
