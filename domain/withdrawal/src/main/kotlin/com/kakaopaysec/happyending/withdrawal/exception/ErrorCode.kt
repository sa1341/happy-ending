package com.kakaopaysec.happyending.withdrawal.exception

import org.springframework.http.HttpStatus

interface AccountErrorCode {
    val status: HttpStatus
    val message: String
}

enum class AccountsErrorCode(
    override val status: HttpStatus,
    override val message: String
) : AccountErrorCode {
    NOT_EXISTS_PENSION_ACCOUNT(HttpStatus.NOT_FOUND, "연금계좌가 존재하지 않습니다."),
    GET_ACCOUNTS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "연금계좌를 조회하던 중 오류가 발생하였습니다."),
    NOT_EXISTS_ACCOUNT_INFO(HttpStatus.NOT_FOUND, "연금계좌가 존재하지 않습니다."),
    GET_ACCOUNT_INFO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "연금계좌정보 조회 중 오류가 발생하였습니다.")
}

enum class AccountNicknameErrorCode(
    override val status: HttpStatus,
    override val message: String
) : AccountErrorCode {
    NOT_EXISTS_ACCOUNT_NICKNAME(HttpStatus.NOT_FOUND, "연금계좌별명이 존재하지 않습니다."),
    ALREADY_EXISTS_ACCOUNT_NICKNAME(HttpStatus.BAD_REQUEST, "연금계좌별명이 이미 존재합니다."),
    GET_ACCOUNT_NICKNAME_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "연금계좌별명 조회 중 오류가 발생하였습니다.")
}

enum class AccountInputErrorCode(
    override val status: HttpStatus,
    override val message: String
) : AccountErrorCode {
    FIELDS_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다.")
}

enum class EvaluationErrorCode(
    override val status: HttpStatus,
    override val message: String
) : AccountErrorCode {
    NOT_EXISTS_PRODUCTS_PROFIT(HttpStatus.NOT_FOUND, "연금계좌 상품평가정보가 존재하지 않습니다."),
    NOT_EXISTS_MONTHLY_CUMULATIVE_PROFITS(HttpStatus.NOT_FOUND, "월말누적수익률 정보가 존재하지 않습니다.")
}
