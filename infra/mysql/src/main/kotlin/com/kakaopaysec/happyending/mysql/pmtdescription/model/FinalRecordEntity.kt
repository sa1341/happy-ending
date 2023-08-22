package com.kakaopaysec.happyending.mysql.pmtdescription.model

import com.kakaopaysec.happyending.mysql.BaseAuditEntity
import com.kakaopaysec.happyending.mysql.pmtdescription.model.type.RecordType
import com.kakaopaysec.happyending.utils.MediaCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "payment_description_final_records")
@Entity
class FinalRecordEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    val recordId: Long? = null,

    @Column(name = "icm_impd_yr")
    val icmImpdYr: String,

    @Column(name = "record_type")
    @Enumerated(EnumType.STRING)
    val recordType: RecordType,

    @Column(name = "acno")
    val accountNumber: String? = null,

    @Column(name = "tr_dt")
    val tradeAt: String? = null,

    @Column(name = "tr_s")
    val tradeSeq: Long? = null,

    @Column(name = "record_content", length = 1000)
    val recordContent: String,

    @Column(name = "request_terminal_type")
    @Enumerated(EnumType.STRING)
    val requestTerminalType: MediaCode
) : BaseAuditEntity() {

    fun addCreatedBy(createdBy: String) {
        super.createdBy = createdBy
    }

    fun addUpdatedBy(updatedBy: String) {
        super.updatedBy = updatedBy
    }
}
