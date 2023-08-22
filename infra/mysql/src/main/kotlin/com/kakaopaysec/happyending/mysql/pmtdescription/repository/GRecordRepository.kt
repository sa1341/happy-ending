package com.kakaopaysec.happyending.mysql.pmtdescription.repository

import com.kakaopaysec.happyending.mysql.pmtdescription.model.GRecordEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GRecordRepository : JpaRepository<GRecordEntity, Long> {
    fun findByAccountNumberAndRtmtSbdoDataTcdAndBaseYearAndCreatedAtAndCreatedSeq(
        accountNumber: String,
        rtmtSbdoDataTcd: String,
        baseYear: String,
        createdAt: String,
        createdSeq: Long
    ): GRecordEntity?

    fun findByTradeAtAndTradeSeqAndAccountNumber(
        tradeAt: String,
        tradeSeq: Long,
        accountNumber: String
    ): GRecordEntity?
}
