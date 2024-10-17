package com.kakaopaysec.happyending.consumer.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ChargeEvent(
    @JsonProperty("service_group_id")
    val serviceGroupId: String,
    @JsonProperty("is_charged")
    val isCharged: Boolean,
    @JsonProperty("is_processed")
    val isProcessed: Boolean,
)

data class FundInformation(
    val fundSequence: Int?,
    val ratio: BigDecimal,
    val itemCode: String,
)
