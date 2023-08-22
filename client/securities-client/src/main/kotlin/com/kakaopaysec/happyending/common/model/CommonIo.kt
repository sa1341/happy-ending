package com.kakaopaysec.happyending.common.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.kakaopaysec.happyending.common.error.SecuritiesErrorCode
import com.kakaopaysec.happyending.common.error.SecuritiesException
import com.kakaopaysec.happyending.model.common.PageRequest
import com.kakaopaysec.happyending.utils.GuidUtils
import com.kakaopaysec.happyending.utils.MediaCode
import com.kakaopaysec.happyending.utils.Profile
import com.kakaopaysec.happyending.utils.SystemCode
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties

private val whiteListErrorCodes = listOf<String>("22718")

@JsonIgnoreProperties(ignoreUnknown = true)
data class MessageResult(
    @JsonProperty("message")
    val message: SecuritiesClientMessage
)

interface AbstractSecuritiesClientResponse {
    val message: SecuritiesClientMessage
    val commonHeader: SecuritiesClientHeader
    fun validate()
}

data class SecuritiesClientMessage(
    @JsonProperty("th_msg_oput_tcd") val messageOutputType: String,
    @JsonProperty("msg_cod") val messageCode: String,
    @JsonProperty("msg_cn") val messageContent: String
) {
    fun isSuccess() = this.messageOutputType == OUTPUT_SUCCESS_TYPE
    companion object {
        const val OUTPUT_SUCCESS_TYPE = "0"
    }
}

data class SecuritiesClientResult<U>(
    override val commonHeader: SecuritiesClientHeader,
    override val message: SecuritiesClientMessage,
    val data: U?,
    val success: Boolean
) : AbstractSecuritiesClientResponse {
    override fun validate() {
        if (message.isSuccess() && data == null) {
            throw SecuritiesException.of(SecuritiesErrorCode.SECURITIES_RESPONSE_DATA_NULL)
        }

        if (!message.isSuccess()) {
            throw SecuritiesException.of(
                errorCode = SecuritiesErrorCode.SECURITIES_SERVER_ERROR,
                securitiesClientMessage = message,
                appUserId = null
            )
        }
    }
}

data class SecuritiesClientCommand<T : Any>(
    val commonHeader: SecuritiesClientHeader,
    val data: T
) {
    companion object {
        fun <R : Any> of(
            serviceId: String,
            profile: Profile,
            requestObject: R,
            mediaCode: MediaCode,
            pageRequest: PageRequest? = null,
            continueTransactionKeyList: List<String> = emptyList(),
            appUserId: String? = null
        ): SecuritiesClientCommand<R> {
            return SecuritiesClientCommand(
                commonHeader = SecuritiesClientHeader.of(
                    profile = profile,
                    serviceId = serviceId,
                    mediaCode = mediaCode,
                    pageRequest = pageRequest,
                    continueTransactionKeyList = continueTransactionKeyList,
                    appUserId = appUserId
                ),
                data = requestObject
            )
        }
    }
}

data class SecuritiesClientHeader(
    @JsonProperty("guid") val guid: String = generateGuid(),
    @JsonProperty("th_tr_id") val serviceId: String,
    @JsonProperty("mda_tcd") val mediaCode: MediaCode,
    @JsonProperty("th_sys_tcd") val systemCode: SystemCode,
    @JsonProperty("onln_user_id") val onlineUserId: String,

    // 고정
    @JsonProperty("th_tr_tcd") val transactionType: String = "S", // 거래 유형 정보 (S: Request)
    @JsonProperty("th_if_tcd") val interfaceType: String = "0", // 호출하고자 하는 대상 시스템 ID (0: 계정계)
    @JsonProperty("blng_dept_cod") val belongDepartmentCode: String = "PAY",
    @JsonProperty("wrk_tmnl_id") val workingTerminalId: String = "PAY000000",
    @JsonProperty("th_conn_loc_tcd") val connectionLocationType: String = "0",

    // 페이징
    @JsonProperty("cont_trkey") val continueTransactionKeyList: List<String> = emptyList(), // 다음페이지키
    @JsonProperty("th_qry_c") val size: String = "20",
    @JsonProperty("th_cont_tr_tcd") val continueTransactionCode: String? = "" // 7: 다음페이지 있음, 4: 다음페이지 없음
) {
    companion object {
        fun generateGuid() = GuidUtils.generateSecuritiesGuid()

        private val defaultPageSize = SpringDataWebProperties().pageable.defaultPageSize

        fun of(
            serviceId: String,
            profile: Profile,
            appUserId: String?,
            mediaCode: MediaCode,
            pageRequest: PageRequest? = null,
            continueTransactionKeyList: List<String> = emptyList()
        ): SecuritiesClientHeader {
            return SecuritiesClientHeader(
                serviceId = serviceId,
                mediaCode = mediaCode,
                systemCode = profile.toSystemCode(),
                onlineUserId = appUserId ?: "PAY",
                size = (pageRequest?.size ?: defaultPageSize).toString(),
                continueTransactionKeyList = continueTransactionKeyList
            )
        }
    }
}
