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
    val limitAmount: Long = 1000000L,
) : BaseAuditEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investment_product_id")
    var id: Long? = null

    fun updateInvestAmount(investmentAmount: Long) {
        if (!isPossibleInvestment()) {
            throw RuntimeException("모집금액한도를 초과하여 더 이상 투자가 불가합니다. 총 모집금액: ${this.investmentAmount}")
        }
        val currentInvestmentAmount = this.investmentAmount.plus(investmentAmount)
        if (limitAmount < currentInvestmentAmount) {
            val balance = currentInvestmentAmount.minus(limitAmount)
            println("남은 잔액: $balance")
            this.investmentAmount = this.investmentAmount.plus(
                investmentAmount.minus(balance)
            )
        } else {
            this.investmentAmount = currentInvestmentAmount
        }
    }

    private fun isPossibleInvestment(): Boolean {
        return investmentAmount < limitAmount
    }
}
