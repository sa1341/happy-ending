package com.kakaopaysec.happyending.mysql.invest.repository

import com.kakaopaysec.happyending.mysql.invest.entity.InvestmentProductHistory
import org.springframework.data.jpa.repository.JpaRepository

interface InvestmentProductHistoryRepository : JpaRepository<InvestmentProductHistory, Long>
