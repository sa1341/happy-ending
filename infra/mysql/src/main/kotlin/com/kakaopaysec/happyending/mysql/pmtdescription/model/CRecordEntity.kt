package com.kakaopaysec.happyending.mysql.pmtdescription.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Table(name = "payment_description_c_records")
@Entity
class CRecordEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    val recordId: Long? = null,

    @Column(name = "acno")
    val accountNumber: String,

    @Column(name = "tr_dt")
    val tradeAt: String,

    @Column(name = "tr_s")
    val tradeSeq: Long,

    @Column(name = "txn_s")
    val txnSeq: Long,

    @Column(name = "tr_cncl_yn")
    val trCnclYn: String,

    @Column(name = "flct_dat_tcd")
    val flctDatTcd: String? = null,

    @Column(name = "rppn_tcd")
    val rppnTcd: String? = null,

    @Column(name = "eabbr_ctry_cod")
    val eabbrCtryCod: String? = null,

    @Column(name = "py_items_nrf_tcd")
    val pyItemsNrfTcd: String? = null,

    @Column(name = "suc_yn")
    val sucYn: String,

    @Column(name = "me_ac_yn")
    val meAcYn: String,

    @Column(name = "pnsa_acp_strt_rqs_dt")
    val pnsaAcpStrtRqsAt: String? = null,

    @Column(name = "dpnt_pan_ea")
    val dpntPanEa: BigDecimal,

    @Column(name = "pnsa_acp_cntf_yr")
    val pnsaAcpCntfYr: String? = null,

    @Column(name = "pan_join_toy_pcd")
    val panJoinToyPcd: String? = null,

    @Column(name = "pnsa_acp_odyr_vl")
    val pnsaAcpOdyrVl: Int,

    @Column(name = "try_pnsa_acp_lmt_a")
    val tryPnsaAcpLmtA: BigDecimal,

    @Column(name = "pnsa_rl_acp_odyr_vl")
    val pnsaRlAcpOdyrVl: Int,

    @Column(name = "icm_impd_ym")
    val icmImpdYm: String? = null,

    @Column(name = "rtmt_icm_a")
    val rtmtIcmA: BigDecimal,

    @Column(name = "pnsa_acp_txn_exp_a")
    val pnsaAcpTxnExpA: BigDecimal,

    @Column(name = "pnsa_acp_tad_a")
    val pnsaAcpTadA: BigDecimal,

    @Column(name = "pnsa_acp_chd_a")
    val pnsaAcpChdA: BigDecimal,

    @Column(name = "pnsa_acp_txdc_a")
    val pnsaAcpTxdcA: BigDecimal,

    @Column(name = "me_wthd_txn_exp_a")
    val meWthdTxnExpA: BigDecimal,

    @Column(name = "me_wthd_tad_a")
    val meWthdTadA: BigDecimal,

    @Column(name = "me_wthd_chd_a")
    val meWthdChdA: BigDecimal,

    @Column(name = "me_wthd_txdc_a")
    val meWthdTxdcA: BigDecimal,

    @Column(name = "invt_rsn_txn_exp_a")
    val invtRsnTxnExpA: BigDecimal,

    @Column(name = "invt_rsn_tad_a")
    val invtRsnTadA: BigDecimal,

    @Column(name = "invt_rsn_chd_a")
    val invtRsnChdA: BigDecimal,

    @Column(name = "invt_rsn_txdc_a")
    val invtRsnTxdcA: BigDecimal,

    @Column(name = "pnsa_oth_acp_txn_exp_a")
    val pnsaOthAcpTxnExpA: BigDecimal,

    @Column(name = "pnsa_oth_acp_tad_a")
    val pnsaOthAcpTadA: BigDecimal,

    @Column(name = "pnsa_oth_acp_chd_a")
    val pnsaOthAcpChdA: BigDecimal,

    @Column(name = "pnsa_oth_acp_txdc_a")
    val pnsaOthAcpTxdcA: BigDecimal,

    @Column(name = "spcl_rsn_tcd")
    val spclRsnTcd: String,

    @Column(name = "pnsa_oth_acp_ac_abnd_yn")
    val pnsaOthAcpAcAbndYn: String,

    @Column(name = "pnsa_oth_acp_pat_wthd_yn")
    val pnsaOthAcpPatWthdYn: String,

    @Column(name = "pnsa_oth_acp_lmt_over_yn")
    val pnsaOthAcpLmtOverYn: String,

    @Column(name = "txn_exp_a")
    val txnExpA: BigDecimal,

    @Column(name = "pnsa_icm_tad_py_a")
    val pnsaIcmTadPyA: BigDecimal,

    @Column(name = "pnsa_icm_tad_txr")
    val pnsaIcmTadTxr: BigDecimal,

    @Column(name = "pnsa_icm_tad_ta")
    val pnsaIcmTadTa: BigDecimal,

    @Column(name = "pnsa_icm_chd_py_a")
    val pnsaIcmChdPyA: BigDecimal,

    @Column(name = "pnsa_icm_chd_txr")
    val pnsaIcmChdTxr: BigDecimal,

    @Column(name = "pnsa_icm_chd_ta")
    val pnsaIcmChdTa: BigDecimal,

    @Column(name = "percent3_py_a")
    val percent3PyA: BigDecimal,

    @Column(name = "percent3_ta")
    val percent3Ta: BigDecimal,

    @Column(name = "percent4_py_a")
    val percent4PyA: BigDecimal,

    @Column(name = "percent4_ta")
    val percent4Ta: BigDecimal,

    @Column(name = "percent5_py_a")
    val percent5PyA: BigDecimal,

    @Column(name = "percent5_ta")
    val percent5Ta: BigDecimal,

    @Column(name = "rtmt_icm_tad_py_a")
    val rtmtIcmTadPyA: BigDecimal,

    @Column(name = "rtmt_icm_tad_txr")
    val rtmtIcmTadTxr: BigDecimal,

    @Column(name = "rtmt_icm_tad_ta")
    val rtmtIcmTadTa: BigDecimal,

    @Column(name = "rtmt_icm_chd_py_a")
    val rtmtIcmChdPyA: BigDecimal,

    @Column(name = "rtmt_icm_chd_txr")
    val rtmtIcmChdTxr: BigDecimal,

    @Column(name = "rtmt_icm_chd_ta")
    val rtmtIcmChdTa: BigDecimal,

    @Column(name = "dpnt_rtmt_icm_py_a")
    val dpntRtmtIcmPyA: BigDecimal,

    @Column(name = "dpnt_rtmt_intx")
    val dpntRtmtIntx: BigDecimal,

    @Column(name = "etc_icm_pnsa_oth_py_a")
    val etcIcmPnsaOthPyA: BigDecimal,

    @Column(name = "etc_icm_pnsa_oth_ta")
    val etcIcmPnsaOthTa: BigDecimal,

    @Column(name = "pnsa_icm_agtxn_py_a")
    val pnsaIcmAgtxnPyA: BigDecimal,

    @Column(name = "pnsa_icm_sptxn_py_a")
    val pnsaIcmSptxnPyA: BigDecimal,

    @Column(name = "pnsa_intx")
    val pnsaIntx: BigDecimal,

    @Column(name = "pnsa_icm_litx")
    val pnsaIcmLitx: BigDecimal,

    @Column(name = "dpnt_pnsa_intx_sum_a")
    val dpntPnsaIntxSumA: BigDecimal,

    @Column(name = "rtmt_icm_py_a")
    val rtmtIcmPyA: BigDecimal,

    @Column(name = "rtmt_intx")
    val rtmtIntx: BigDecimal,

    @Column(name = "rtmt_icm_litx")
    val rtmtIcmLitx: BigDecimal,

    @Column(name = "dpnt_rix_sum_a")
    val dpntRixSumA: BigDecimal,

    @Column(name = "etc_icm_py_a")
    val etcIcmPyA: BigDecimal,

    @Column(name = "dpnt_etc_intx")
    val dpntEtcIntx: BigDecimal,

    @Column(name = "dpnt_etc_icm_litx")
    val dpntEtcIcmLitx: BigDecimal,

    @Column(name = "dpnt_etc_intx_sum_a")
    val dpntEtcIntxSumA: BigDecimal,

    @Column(name = "levy_rbr_tcd")
    val levyRbrTcd: String,

    @Column(name = "levy_rbr_otr_dt")
    val levyRbrOtrAt: String? = null,

    @Column(name = "levy_rbr_otr_s")
    val levyRbrOtrS: Long,

    @Column(name = "levy_rbr_otr_ss")
    val levyRbrOtrSs: Long,

    @Column(name = "levy_rbr_obj_tr_dt")
    val levyRbrObjTrAt: String? = null,

    @Column(name = "levy_rbr_obj_tr_s")
    val levyRbrObjTrS: Long,

    @Column(name = "levy_rbr_obj_tr_ss")
    val levyRbrObjTrSs: Long,

    @Column(name = "wker_id")
    val wkerId: String,

    @Column(name = "wrk_tmnl_id")
    val wrkTmnlId: String,

    @Column(name = "wrk_dl_dttm")
    val wrkDlDttm: String,

    @Column(name = "guid")
    val guid: String,

    @Column(name = "whld_prmkey_cn")
    val whldPrmkeyCn: String? = null,

    @Column(name = "rbr_yn")
    val rbrYn: String,

    @Column(name = "org_pnsa_icm_tad_ta")
    val orgPnsaIcmTadTa: BigDecimal? = null,

    @Column(name = "org_pnsa_icm_chd_ta")
    val orgPnsaIcmChdTa: BigDecimal? = null,

    @Column(name = "org_percent3_ta")
    val orgPercent3Ta: BigDecimal? = null,

    @Column(name = "org_percent4_ta")
    val orgPercent4Ta: BigDecimal? = null,

    @Column(name = "org_percent5_ta")
    val orgPercent5Ta: BigDecimal? = null,

    @Column(name = "org_rtmt_icm_tad_ta")
    val orgRtmtIcmTadTa: BigDecimal? = null,

    @Column(name = "org_rtmt_icm_chd_ta")
    val orgRtmtIcmChdTa: BigDecimal? = null,

    @Column(name = "org_dpnt_rix")
    val orgDpntRix: BigDecimal? = null,

    @Column(name = "org_etc_icm_pnsa_oth_ta")
    val orgEtcIcmPnsaOthTa: BigDecimal? = null,

    @Column(name = "org_pnsa_intx")
    val orgPnsaIntx: BigDecimal? = null,

    @Column(name = "org_pnsa_icm_litx")
    val orgPnsaIcmLitx: BigDecimal? = null,

    @Column(name = "org_dpnt_pnsa_intx_sum_a")
    val orgDpntPnsaIntxSumA: BigDecimal? = null,

    @Column(name = "org_rix")
    val orgRix: BigDecimal? = null,

    @Column(name = "org_rtmt_icm_litx")
    val orgRtmtIcmLitx: BigDecimal? = null,

    @Column(name = "org_dpnt_rix_sum_a")
    val orgDpntRixSumA: BigDecimal? = null,

    @Column(name = "org_dpnt_etc_intx")
    val orgDpntEtcIntx: BigDecimal? = null,

    @Column(name = "org_dpnt_etc_icm_litx")
    val orgDpntEtcIcmLitx: BigDecimal? = null,

    @Column(name = "org_dpnt_etc_intx_sum_a")
    val orgDpntEtcIntxSumA: BigDecimal? = null,
)
