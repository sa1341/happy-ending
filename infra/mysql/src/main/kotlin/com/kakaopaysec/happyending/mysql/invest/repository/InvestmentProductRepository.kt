package com.kakaopaysec.happyending.mysql.invest.repository

import com.kakaopaysec.happyending.mysql.invest.entity.InvestmentProduct
import org.springframework.data.jpa.repository.JpaRepository

interface InvestmentProductRepository : JpaRepository<InvestmentProduct, Long> {
    fun findByFundCode(fundCode: String): InvestmentProduct?
}
