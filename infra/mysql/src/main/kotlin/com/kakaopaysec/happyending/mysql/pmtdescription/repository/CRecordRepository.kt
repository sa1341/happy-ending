package com.kakaopaysec.happyending.mysql.pmtdescription.repository

import com.kakaopaysec.happyending.mysql.pmtdescription.model.CRecordEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CRecordRepository : JpaRepository<CRecordEntity, Long> {
    fun findByAccountNumberAndTradeAtAndTradeSeqAndTxnSeq(
        accountNumber: String,
        tradeAt: String,
        tradeSeq: Long,
        txnSeq: Long,
    ): CRecordEntity?
}
