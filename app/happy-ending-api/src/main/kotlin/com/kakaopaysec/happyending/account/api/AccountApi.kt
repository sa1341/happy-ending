package com.kakaopaysec.happyending.account.api

import com.kakaopaysec.happyending.account.entity.Account
import com.kakaopaysec.happyending.account.repository.AccountRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient

@RequestMapping("/v1/account")
@RestController
class AccountApi(
    @Qualifier("happyEndingClient")
    private val webClient: WebClient,
    private val accountRepository: AccountRepository
) {

    @GetMapping
    fun getAccountInfo(@RequestParam(value = "accountId") accountId: Long): Account? {
        return accountRepository.findByIdOrNull(accountId)
    }

    @GetMapping("/client-test")
    fun getPensionAccount(): String {
        return "hi"
    }
}

data class SecretDto(
    val id: String,
    val password: String
)
