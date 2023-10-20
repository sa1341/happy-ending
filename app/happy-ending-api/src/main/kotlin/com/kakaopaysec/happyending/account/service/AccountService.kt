package com.kakaopaysec.happyending.account.service

import com.kakaopaysec.happyending.account.model.AccountRegistrationRequest
import com.kakaopaysec.happyending.account.model.AccountRegistrationResponse
import com.kakaopaysec.happyending.mysql.account.entity.Account
import com.kakaopaysec.happyending.mysql.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository
) {
    fun saveAccountNumber(request: AccountRegistrationRequest): AccountRegistrationResponse {
        val accountEntity = accountRepository.save(
            Account(accountNo = request.accountNumber, name = request.name)
        )

        return AccountRegistrationResponse.from(accountEntity)
    }

    fun getAccount(accountNumber: String): Account? {
        return accountRepository.findByAccountNo(accountNumber)
    }
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
