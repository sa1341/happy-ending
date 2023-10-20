package com.kakaopaysec.happyending.mysql.account.repository

import com.kakaopaysec.happyending.mysql.account.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByAccountNo(accountNumber: String): Account?
}
