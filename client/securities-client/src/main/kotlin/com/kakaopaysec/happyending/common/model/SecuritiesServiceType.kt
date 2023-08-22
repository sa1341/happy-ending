package com.kakaopaysec.happyending.common.model

enum class SecuritiesServiceType(
    val serviceId: String,
    val description: String,
    val isRetry: Boolean? = false
) {
    ACCOUNT_LIST(serviceId = "TACQ7231", description = "계좌목록공통조회"),
    GET_ACCOUNT_NICKNAME("TPPQ9100", "연금계좌별칭조회"),
    SAVE_ACCOUNT_NICKNAME("TPPU9101", "연금계좌별칭저장"),
    SAVE_ACCOUNT_INFO(serviceId = "TPPU9000", description = "연금계좌정보저장"),
    GET_ACCOUNT_INFO("TPPQ9001", "연금계좌정보조회"),
    GET_ASSET_VALUATION("TPPQ9300", "상품별 평가정보 조회"),
    GET_ACCUMULATED_PRIFITS_BY_MONTHS("TPPQ9320", "기간별 월말 누적수익률 조회"),
    GET_ACCUMULATED_PROFIT("TPPQ9321", "총 누적수익률 조회"),
    GET_KFB_LIMIT_BALANCE("TPPQ9200", "은행연합회 한도 잔액 조회"),
    GET_WITHDRAWAL_TRANSACTION("TPPQ9900", "연금계좌 출금내역 상세조회"),
    GET_TAXATION_AMOUNT("TPPQ9340", "연금계좌 금액명세 항목 조회"),
    GET_WITHDRAWAL_TAX("TPPQ9350", "연금계좌 출금금액 세금정보 조회"),
    GET_KFB_REGISTERED_ACCOUNTS("TPPQ9210", "은행연합회한도 가입내역조회"),
    GET_EXPECTATION_TAX_DEDUCTION("TPPQ9310", "세액공제 예상금액 조회"),
    GET_ACCOUNT_ABLE_DATE_AND_AGE("TPPQ9002", "연금계좌 개시가능일자 및 개시가능연령 조회"),
    ISSUE_ACCOUNT_CERTIFICATE("TPPU9800", "계좌확인서 이메일 발송")
}
