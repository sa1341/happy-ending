package com.kakaopaysec.happyending.batch.investment.model

data class InvestmentProductRequest(
    val fundCode: String,
    val investmentAmount: Long
)

data class InvestmentProductResponse(
    val isSuccess: Boolean
)
