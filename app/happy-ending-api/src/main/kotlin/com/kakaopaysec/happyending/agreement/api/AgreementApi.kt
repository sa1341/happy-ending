package com.kakaopaysec.happyending.agreement.api

import com.kakaopaysec.happyending.agreement.service.AgreementService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/agreements")
@RestController
class AgreementApi(
    private val serviceAgreementService: AgreementService
) {

    @PostMapping("/recurring-payment")
    fun createAgreement(@RequestBody createAgreementRequest: CreateAgreementRequest) {
        serviceAgreementService.create(createAgreementRequest)
    }
}

data class CreateAgreementRequest(
    val accountNumber: String,
    val serviceGroupId: String
)
