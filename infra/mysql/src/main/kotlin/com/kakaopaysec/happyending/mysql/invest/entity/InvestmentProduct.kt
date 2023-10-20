package com.kakaopaysec.happyending.mysql.invest.entity

import com.kakaopaysec.happyending.mysql.BaseAuditEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

@Table(name = "investment_product")
@DynamicUpdate
@Entity
class InvestmentProduct(
    @Column(name = "fund_code")
    val fundCode: String,
    @Column(name = "investment_amount")
    var investmentAmount: Long,
    @Column(name = "limit_amount")
    val limitAmount: Long = 1000000L
) : BaseAuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investment_product_id")
    var id: Long? = null

    fun updateInvestAmount(investmentAmount: Long) {
        if (!isCompletedInvestment(investmentAmount)) {
            throw RuntimeException("모집금액한도를 초과하여 더 이상 투자가 불가합니다. 총 모집금액: ${this.investmentAmount}")
        }
        this.investmentAmount = investmentAmount
    }

    private fun isCompletedInvestment(investmentAmount: Long): Boolean {
        return this.investmentAmount <= this.limitAmount
    }
}
