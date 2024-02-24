package com.kakaopaysec.happyending.mysql.agreement.repository.search

import com.kakaopaysec.happyending.mysql.agreement.entity.ServiceAgreement
import com.kakaopaysec.happyending.mysql.agreement.entity.ServiceAgreementGroup
import com.querydsl.core.annotations.QueryProjection

data class ServiceAgreementVo @QueryProjection constructor(
    val serviceAgreementGroup: ServiceAgreementGroup,
    val serviceAgreement: ServiceAgreement
)
