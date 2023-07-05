package com.kakaopaysec.happyending.account.api

import com.kakaopaysec.happyending.account.entity.Account
import com.kakaopaysec.happyending.account.repository.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/v1/account")
@RestController
class AccountApi(
    private val accountRepository: AccountRepository
) {

    @GetMapping
    fun getAccountInfo(@RequestParam(value = "accountId") accountId: Long): Account? {
        return accountRepository.findByIdOrNull(accountId)
    }
}
