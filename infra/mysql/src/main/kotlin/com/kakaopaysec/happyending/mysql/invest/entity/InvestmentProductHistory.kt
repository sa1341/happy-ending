package com.kakaopaysec.happyending.mysql.invest.entity

import com.kakaopaysec.happyending.mysql.BaseAuditEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Table(name = "investment_product_history")
@DynamicUpdate
@Entity
class InvestmentProductHistory(
    val accountNumber: String,
    val fundCode: String,
    val investAmount: Long,
    val investmentProductId: Long?
) : BaseAuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investment_product_history_id")
    var id: Long? = null
}
