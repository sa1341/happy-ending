package com.kakaopaysec.happyending.mysql.agreement

import com.kakaopaysec.happyending.mysql.agreement.entity.ServiceAgreement
import com.kakaopaysec.happyending.mysql.agreement.entity.ServiceAgreementGroup
import com.kakaopaysec.happyending.mysql.agreement.repository.ServiceAgreementGroupRepository
import com.kakaopaysec.happyending.mysql.agreement.repository.ServiceAgreementRepository
import com.kakaopaysec.happyending.mysql.support.DataJpaTestSupport
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*

@ActiveProfiles("test")
class ServiceAgreementRepositoryTest @Autowired constructor(
    private val serviceAgreementRepository: ServiceAgreementRepository,
    private val serviceAgreementGroupRepository: ServiceAgreementGroupRepository
) : DataJpaTestSupport() {

    @Test
    @Transactional
    fun `약관서비스 정보를 조회한다`() {
        // given
        val serviceAgreementGroup = ServiceAgreementGroup(
            serviceCode = serviceCode,
            serviceName = serviceName,
            accountNumber = accountNumber,
            serviceGroupId = groupId
        )

        val serviceAgreement = ServiceAgreement(
            serviceCode = serviceCode,
            accountNumber = accountNumber,
            serviceGroupId = groupId,
            status = "ACTIVATED"
        )

        // when
        serviceAgreementGroupRepository.save(serviceAgreementGroup)
        serviceAgreementRepository.save(serviceAgreement)

        val serviceAgreementVo = serviceAgreementGroupRepository.findServiceAgreementBy(
            serviceCode = serviceCode,
            accountNumber = accountNumber
        )

        val findServiceAgreement = serviceAgreementVo?.serviceAgreement ?: throw RuntimeException("test")

        // then
        findServiceAgreement.modifyStatus("DEACTIVATED")
    }

    companion object {
        val groupId = "PAYSEC20240223235959"
        val accountNumber = "02000162758"
        val serviceName = "AUTO_PAYMENT"
        val serviceCode = "0110"
    }
}
