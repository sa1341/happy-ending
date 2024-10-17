package com.kakaopaysec.happyending.mysql.pmtdescription.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Table(name = "payment_description_d_records")
@Entity
class DRecordEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    val recordId: Long? = null,

    @Column(name = "acno")
    val accountNumber: String,

    @Column(name = "bas_yr")
    val baseYear: String,

    @Column(name = "crn_dt")
    val createdAt: String,

    @Column(name = "crn_s")
    val createdSeq: Long,

    @Column(name = "past_txn_exp_inc_trf_a")
    val pastTxnExpIncTrfA: BigDecimal,

    @Column(name = "past_txn_exp_inc_mnyi_a")
    val pastTxnExpIncMnyiA: BigDecimal,

    @Column(name = "past_txn_exp_dcr_trf_a")
    val pastTxnExpDcrTrfA: BigDecimal,

    @Column(name = "past_txn_exp_dcr_wthd_a")
    val pastTxnExpDcrWthdA: BigDecimal,

    @Column(name = "tstrm_txn_exp_inc_trf_a")
    val tstrmTxnExpIncTrfA: BigDecimal,

    @Column(name = "tstrm_txn_exp_inc_mnyi_a")
    val tstrmTxnExpIncMnyiA: BigDecimal,

    @Column(name = "tstrm_txn_exp_dcr_trf_a")
    val tstrmTxnExpDcrTrfA: BigDecimal,

    @Column(name = "tstrm_txn_exp_dcr_wthd_a")
    val tstrmTxnExpDcrWthdA: BigDecimal,

    @Column(name = "past_chd_rtmt_inc_trf_a")
    val pastChdRtmtIncTrfA: BigDecimal,

    @Column(name = "past_chd_rtmt_inc_mnyi_a")
    val pastChdRtmtIncMnyiA: BigDecimal,

    @Column(name = "past_chd_rtmt_dcr_trf_a")
    val pastChdRtmtDcrTrfA: BigDecimal,

    @Column(name = "past_chd_rtmt_dcr_wthd_a")
    val pastChdRtmtDcrWthdA: BigDecimal,

    @Column(name = "tstrm_chd_rtmt_inc_trf_a")
    val tstrmChdRtmtIncTrfA: BigDecimal,

    @Column(name = "tstrm_chd_rtmt_inc_mnyi_a")
    val tstrmChdRtmtIncMnyiA: BigDecimal,

    @Column(name = "tstrm_chd_rtmt_dcr_trf_a")
    val tstrmChdRtmtDcrTrfA: BigDecimal,

    @Column(name = "tstrm_chd_rtmt_dcr_wthd_a")
    val tstrmChdRtmtDcrWthdA: BigDecimal,

    @Column(name = "past_tad_rtmt_inc_trf_a")
    val pastTadRtmtIncTrfA: BigDecimal,

    @Column(name = "past_tad_rtmt_inc_mnyi_a")
    val pastTadRtmtIncMnyiA: BigDecimal,

    @Column(name = "past_tad_rtmt_dcr_trf_a")
    val pastTadRtmtDcrTrfA: BigDecimal,

    @Column(name = "past_tad_rtmt_dcr_wthd_a")
    val pastTadRtmtDcrWthdA: BigDecimal,

    @Column(name = "tstrm_tad_rtmt_inc_trf_a")
    val tstrmTadRtmtIncTrfA: BigDecimal,

    @Column(name = "tstrm_tad_rtmt_inc_mnyi_a")
    val tstrmTadRtmtIncMnyiA: BigDecimal,

    @Column(name = "tstrm_tad_rtmt_dcr_trf_a")
    val tstrmTadRtmtDcrTrfA: BigDecimal,

    @Column(name = "tstrm_tad_rtmt_dcr_wthd_a")
    val tstrmTadRtmtDcrWthdA: BigDecimal,

    @Column(name = "past_txdc_inc_trf_a")
    val pastTxdcIncTrfA: BigDecimal,

    @Column(name = "past_txdc_inc_mnyi_a")
    val pastTxdcIncMnyiA: BigDecimal,

    @Column(name = "past_txdc_dcr_trf_a")
    val pastTxdcDcrTrfA: BigDecimal,

    @Column(name = "past_txdc_dcr_wthd_a")
    val pastTxdcDcrWthdA: BigDecimal,

    @Column(name = "tstrm_txdc_inc_trf_a")
    val tstrmTxdcIncTrfA: BigDecimal,

    @Column(name = "tstrm_txdc_inc_mnyi_a")
    val tstrmTxdcIncMnyiA: BigDecimal,

    @Column(name = "tstrm_txdc_dcr_trf_a")
    val tstrmTxdcDcrTrfA: BigDecimal,

    @Column(name = "tstrm_txdc_dcr_wthd_a")
    val tstrmTxdcDcrWthdA: BigDecimal,

    @Column(name = "or_ern_bef_eot_ba")
    val orErnBefEotBa: BigDecimal,

    @Column(name = "or_ern_tstrm_ba")
    val orErnTstrmBa: BigDecimal,

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
    val guid: String,
)
