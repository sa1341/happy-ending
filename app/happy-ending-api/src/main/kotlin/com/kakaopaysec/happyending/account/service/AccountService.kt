package com.kakaopaysec.happyending.account.service

import org.springframework.stereotype.Service

@Service
class AccountService {

    fun getAccountNumber(
        appUserId: String,
        accountNumber: String
    ): AccountResponse {
        return AccountResponse(
            accountNumber = accountNumber,
            appUserId = appUserId
        )
    }
}

data class AccountResponse(
    val accountNumber: String,
    val appUserId: String
)
