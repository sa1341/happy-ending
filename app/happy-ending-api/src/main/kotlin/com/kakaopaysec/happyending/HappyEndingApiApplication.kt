package com.kakaopaysec.happyending

import com.kakaopaysec.happyending.account.entity.Account
import com.kakaopaysec.happyending.account.repository.AccountRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.kakaopaysec.happyending"])
class HappyEndingApiApplication(
    private val accountRepository: AccountRepository
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val account = Account(accountNo = "02000162758", name = "junyoung")
        accountRepository.save(account)
    }
}

fun main(args: Array<String>) {
    runApplication<HappyEndingApiApplication>(*args)
}
