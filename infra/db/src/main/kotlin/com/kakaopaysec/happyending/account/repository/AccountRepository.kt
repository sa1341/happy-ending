package com.kakaopaysec.happyending.account.repository

import com.kakaopaysec.happyending.account.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long>
