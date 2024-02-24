package com.kakaopaysec.happyending.agreement.service

import com.kakaopaysec.happyending.agreement.api.CreateAgreementRequest
import com.kakaopaysec.happyending.mysql.agreement.entity.ServiceAgreement
import com.kakaopaysec.happyending.mysql.agreement.entity.ServiceAgreementGroup
import com.kakaopaysec.happyending.mysql.agreement.repository.ServiceAgreementGroupRepository
import com.kakaopaysec.happyending.mysql.agreement.repository.ServiceAgreementRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AgreementService(
    private val serviceAgreementGroupRepository: ServiceAgreementGroupRepository,
    private val serviceAgreementRepository: ServiceAgreementRepository
) {

    fun create(createAgreementRequest: CreateAgreementRequest) {
        val serviceAgreementGroup = ServiceAgreementGroup(
            serviceGroupId = createAgreementRequest.serviceGroupId,
            accountNumber = createAgreementRequest.accountNumber,
            serviceName = "AUTO_PAYMENT",
            serviceCode = "0110"
        )

        val serviceAgreement = ServiceAgreement(
            serviceGroupId = createAgreementRequest.serviceGroupId,
            accountNumber = createAgreementRequest.accountNumber,
            serviceCode = "0110",
            status = "ACTIVATED"
        )

        serviceAgreementGroupRepository.save(serviceAgreementGroup)
        serviceAgreementRepository.save(serviceAgreement)

        val serviceAgreementVo = serviceAgreementGroupRepository.findServiceAgreementBy(
            serviceCode = "0110",
            accountNumber = createAgreementRequest.accountNumber
        ) ?: throw RuntimeException("service agreement is not exist")

        val findServiceAgreement = serviceAgreementVo.serviceAgreement
        findServiceAgreement.modifyStatus("DEACTIVATED")
    }
}
