package com.kakaopaysec.happyending.mysql.agreement.repository

import com.kakaopaysec.happyending.mysql.agreement.entity.QServiceAgreement
import com.kakaopaysec.happyending.mysql.agreement.entity.QServiceAgreementGroup
import com.kakaopaysec.happyending.mysql.agreement.entity.ServiceAgreementGroup
import com.kakaopaysec.happyending.mysql.agreement.repository.search.QServiceAgreementVo
import com.kakaopaysec.happyending.mysql.agreement.repository.search.ServiceAgreementVo
import com.kakaopaysec.happyending.mysql.support.QuerydslCustomRepository
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceAgreementGroupRepository : JpaRepository<ServiceAgreementGroup, Long>, ServiceAgreementGroupCustomRepository

interface ServiceAgreementGroupCustomRepository {
    fun findServiceAgreementBy(serviceCode: String, accountNumber: String): ServiceAgreementVo?
}

class ServiceAgreementGroupCustomRepositoryImpl : ServiceAgreementGroupCustomRepository,
    QuerydslCustomRepository(ServiceAgreementGroup::class.java) {

    private val serviceAgreementGroup = QServiceAgreementGroup.serviceAgreementGroup
    private val serviceAgreement = QServiceAgreement.serviceAgreement

    override fun findServiceAgreementBy(serviceCode: String, accountNumber: String): ServiceAgreementVo? {
        return select(
            QServiceAgreementVo(
                serviceAgreementGroup,
                serviceAgreement
            )
        ).from(serviceAgreement)
            .join(serviceAgreementGroup)
            .on(serviceAgreementGroup.serviceGroupId.eq(serviceAgreement.serviceGroupId))
            .where(
                serviceAgreementGroup.serviceCode.eq(serviceCode)
                    .and(serviceAgreementGroup.accountNumber.eq(accountNumber))
            ).fetchOne()
    }
}
