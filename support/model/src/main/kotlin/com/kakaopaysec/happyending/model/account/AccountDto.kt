package com.kakaopaysec.happyending.model.account

import java.math.BigDecimal

open class AccountNumber(
    open val accountNumber: String,
) {
    override fun toString(): String {
        return "AccountNumber(accountNumber=$accountNumber)"
    }
}

data class AccountInfoSaveRequest(
    override val accountNumber: String,
    val receivingStartAge: Int,
    val depositLimitAmount: BigDecimal,
) : AccountNumber(accountNumber)
