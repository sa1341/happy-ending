package com.kakaopaysec.happyending.mysql.fund.repository

import com.kakaopaysec.happyending.mysql.fund.entity.FundProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FundProductRepository : JpaRepository<FundProductEntity, Long> {
    fun findByCode(code: String): FundProductEntity?
}
