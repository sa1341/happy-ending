package com.kakaopaysec.happyending.mysql.pmtdescription.repository

import com.kakaopaysec.happyending.mysql.pmtdescription.model.FinalRecordEntity
import com.kakaopaysec.happyending.mysql.pmtdescription.model.type.RecordType
import org.springframework.data.jpa.repository.JpaRepository

interface FinalRecordRepository : JpaRepository<FinalRecordEntity, Long> {
    fun findByIcmImpdYrAndRecordType(
        icmImpdYr: String,
        recordType: RecordType,
    ): FinalRecordEntity?
}
