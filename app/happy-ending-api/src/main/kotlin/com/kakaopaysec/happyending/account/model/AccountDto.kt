package com.kakaopaysec.happyending.account.model

import com.kakaopaysec.happyending.mysql.account.entity.Account

data class AccountRegistrationRequest(
    val name: String,
    val accountNumber: String
)

data class AccountRegistrationResponse(
    val id: Long?,
    val name: String,
    val accountNumber: String
) {
    companion object {
        fun from(account: Account): AccountRegistrationResponse {
            return AccountRegistrationResponse(
                id = account.id,
                name = account.name,
                accountNumber = account.accountNo
            )
        }
    }
}
