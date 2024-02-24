package com.kakaopaysec.happyending.mysql.agreement.repository

import com.kakaopaysec.happyending.mysql.agreement.entity.ServiceAgreement
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceAgreementRepository : JpaRepository<ServiceAgreement, Long> {
    fun findByServiceCodeAndAccountNumber(serviceCode: String, accountNumber: String): ServiceAgreement?
}
