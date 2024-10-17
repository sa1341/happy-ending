package com.kakaopaysec.happyending.account.api

import com.kakaopaysec.happyending.account.model.AccountRegistrationRequest
import com.kakaopaysec.happyending.account.model.AccountRegistrationResponse
import com.kakaopaysec.happyending.account.service.AccountResponse
import com.kakaopaysec.happyending.account.service.AccountService
import com.kakaopaysec.happyending.config.PaySecUser
import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RequestMapping("/v1/account")
@RestController
class AccountApi(
    private val accountService: AccountService,
) {

    @PostMapping("/get")
    fun getPensionAccount(
        paySecUser: PaySecUser,
        @RequestBody accountNumber: AccountNumber,
    ): AccountResponse {
        log.debug { "PaySecUser: $paySecUser, AccountNumber: ${accountNumber.accountNumber}" }
        return accountService.getAccountNumber(
            appUserId = paySecUser.appUserId,
            accountNumber = accountNumber.accountNumber
        )
    }

    @PostMapping
    fun saveFundProduct(@RequestBody request: AccountRegistrationRequest): AccountRegistrationResponse {
        log.info { "FundProductRequest = $request" }
        return accountService.saveAccountNumber(request)
    }
}

open class AccountNumber(
    open val accountNumber: String,
)

data class SecretDto(
    val id: String,
    val password: String,
)
