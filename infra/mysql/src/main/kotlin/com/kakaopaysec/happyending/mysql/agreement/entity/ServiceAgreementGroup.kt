package com.kakaopaysec.happyending.mysql.agreement.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "service_agreement_group")
@Entity
class ServiceAgreementGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_agreement_group_id")
    val id: Long? = null,
    val serviceCode: String,
    val serviceName: String,
    val accountNumber: String,
    val serviceGroupId: String,
)
