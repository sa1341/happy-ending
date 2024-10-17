package com.kakaopaysec.happyending.mysql.pmtdescription.repository

import com.kakaopaysec.happyending.mysql.pmtdescription.model.DRecordEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DRecordRepository : JpaRepository<DRecordEntity, Long> {
    fun findByAccountNumberAndBaseYearAndCreatedAtAndCreatedSeq(
        accountNumber: String,
        baseYear: String,
        createdAt: String,
        createdSeq: Long,
    ): DRecordEntity?

    fun findByTradeAtAndTradeSeqAndAccountNumber(
        tradeAt: String,
        tradeSeq: Long,
        accountNumber: String,
    ): DRecordEntity?
}
