package com.kakaopaysec.happyending.withdrawal.pmtdescription.service

import com.kakaopaysec.happyending.mysql.pmtdescription.repository.CRecordRepository
import com.kakaopaysec.happyending.mysql.pmtdescription.repository.DRecordRepository
import com.kakaopaysec.happyending.mysql.pmtdescription.repository.FinalRecordRepository
import com.kakaopaysec.happyending.mysql.pmtdescription.repository.GRecordRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class PaymentDescriptionService(
    private val cRecordRepository: CRecordRepository,
    private val dRecordRepository: DRecordRepository,
    private val gRecordRepository: GRecordRepository,
    private val finalRecordRepository: FinalRecordRepository,
)
