package com.kakaopaysec.happyending.mysql.pmtdescription.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Table(name = "payment_description_g_records")
@Entity
class GRecordEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    val recordId: Long? = null,

    @Column(name = "acno")
    val accountNumber: String,

    @Column(name = "rtmt_sbdo_data_tcd")
    val rtmtSbdoDataTcd: String,

    @Column(name = "bas_yr")
    val baseYear: String,

    @Column(name = "crn_dt")
    val createdAt: String,

    @Column(name = "crn_s")
    val createdSeq: Long,

    @Column(name = "dfr_rtmt_icm_tcd")
    val dfrRtmtIcmTcd: String,

    @Column(name = "dfr_rtmt_icm_inc_tmod")
    val dfrRtmtIcmIncTmod: Int,

    @Column(name = "dfr_rtmt_icm_ina")
    val dfrRtmtIcmIna: BigDecimal,

    @Column(name = "dfr_rtmt_intx_ina")
    val dfrRtmtIntxIna: BigDecimal,

    @Column(name = "dfr_rtmt_icm_a")
    val dfrRtmtIcmA: BigDecimal,

    @Column(name = "dfr_rtmt_intx")
    val dfrRtmtIntx: BigDecimal,

    @Column(name = "dfr_rtmt_icm_pnsa_oth_acp_txr")
    val dfrRtmtIcmPnsaOthAcpTxr: BigDecimal,

    @Column(name = "dfr_rtmt_icm_dea")
    val dfrRtmtIcmDea: BigDecimal,

    @Column(name = "dfr_rtmt_intx_dea")
    val dfrRtmtIntxDea: BigDecimal,

    @Column(name = "dfr_rtmt_icm_ba")
    val dfrRtmtIcmBa: BigDecimal,

    @Column(name = "dfr_rtmt_intx_ba")
    val dfrRtmtIntxBa: BigDecimal,

    @Column(name = "tr_dt")
    val tradeAt: String? = null,

    @Column(name = "tr_s")
    val tradeSeq: Long? = null,

    @Column(name = "infortn_yn")
    val infortnYn: String? = null,

    @Column(name = "wker_id")
    val wkerId: String,

    @Column(name = "wrk_tmnl_id")
    val wrkTmnlId: String,

    @Column(name = "wrk_dl_dttm")
    val wrkDlDttm: String,

    @Column(name = "guid")
    val guid: String
)
