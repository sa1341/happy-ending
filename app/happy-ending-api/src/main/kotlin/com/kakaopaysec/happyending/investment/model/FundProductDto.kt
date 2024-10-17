package com.kakaopaysec.happyending.investment.model

import com.kakaopaysec.happyending.mysql.fund.entity.FundProductEntity

data class FundProductRequest(
    val code: String,
    val name: String,
)

data class FundProductResponse(
    val fundProductId: Long?,
    val code: String,
    val name: String,
) {
    companion object {
        fun from(fundProductEntity: FundProductEntity): FundProductResponse {
            return FundProductResponse(
                fundProductEntity.id,
                fundProductEntity.code,
                fundProductEntity.name
            )
        }
    }
}

data class InvestmentProductRequest(
    val fundCode: String,
    val investmentAmount: Long,
)

data class InvestmentProductResponse(
    val id: Long?,
    val isSuccess: Boolean,
)
