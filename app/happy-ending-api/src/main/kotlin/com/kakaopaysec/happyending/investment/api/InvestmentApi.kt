package com.kakaopaysec.happyending.investment.api

import com.kakaopaysec.happyending.investment.model.FundProductRequest
import com.kakaopaysec.happyending.investment.model.FundProductResponse
import com.kakaopaysec.happyending.investment.model.InvestmentProductRequest
import com.kakaopaysec.happyending.investment.model.InvestmentProductResponse
import com.kakaopaysec.happyending.investment.service.InvestmentProductService
import com.kakaopaysec.happyending.mysql.fund.entity.FundProductEntity
import com.kakaopaysec.happyending.mysql.fund.repository.FundProductRepository
import mu.KotlinLogging
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RequestMapping("/api/v1/investment")
@RestController
class InvestmentApi(
    private val investmentProductService: InvestmentProductService,
    private val fundProductRepository: FundProductRepository,
) {

    @PostMapping("participating-investment/{account_number}")
    fun participateInvestmentEvent(
        @PathVariable("account_number") accountNumber: String,
        @RequestBody request: InvestmentProductRequest,
    ): InvestmentProductResponse {
        return investmentProductService.participateInvestmentEvent(
            accountNumber = accountNumber,
            request = request
        )
    }

    /**
     * 단순 펀드상품정보 등록 API
     */
    @PostMapping("/fund")
    fun saveFundProduct(@RequestBody request: FundProductRequest): FundProductResponse {
        log.info { "FundProductRequest = $request" }
        val fundProductEntity = fundProductRepository.save(
            FundProductEntity(request.code, request.name)
        )

        return FundProductResponse.from(fundProductEntity)
    }
}
