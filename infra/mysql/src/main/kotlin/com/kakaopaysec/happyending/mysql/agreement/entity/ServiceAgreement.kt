package com.kakaopaysec.happyending.mysql.agreement.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "service_agreement")
@Entity
class ServiceAgreement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_agreement_id")
    val id: Long? = null,
    val serviceCode: String,
    val accountNumber: String,
    var status: String,
    val serviceGroupId: String,
) {
    fun modifyStatus(changedStatus: String) {
        this.status = changedStatus
    }
}
