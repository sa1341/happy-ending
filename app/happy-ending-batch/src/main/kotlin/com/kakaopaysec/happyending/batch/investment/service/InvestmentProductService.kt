package com.kakaopaysec.happyending.batch.investment.service

import com.kakaopaysec.happyending.batch.investment.model.InvestmentProductRequest
import com.kakaopaysec.happyending.batch.investment.model.InvestmentProductResponse
import com.kakaopaysec.happyending.mysql.invest.entity.InvestmentProduct
import com.kakaopaysec.happyending.mysql.invest.repository.InvestmentProductRepository
import com.kakaopaysec.happyending.service.SecuritiesClientService
import com.kakaopaysec.happyending.service.ServiceType
import com.kakaopaysec.happyending.utils.typeReference
import org.springframework.stereotype.Component

@Component
class InvestmentProductService(
    private val investmentProductRepository: InvestmentProductRepository,
    private val securitiesClientService: SecuritiesClientService
) {

    fun callApi(investmentProductRequest: InvestmentProductRequest) {
        securitiesClientService.callApi(
            serviceType = ServiceType.INVESTMENT_PRODUCT,
            investmentProductRequest = com.kakaopaysec.happyending.service.InvestmentProductRequest(
                fundCode = investmentProductRequest.fundCode,
                investmentAmount = investmentProductRequest.investmentAmount
            ),
            responseType = typeReference<InvestmentProductResponse>()
        )
    }

    fun findAllInvestmentProducts(): List<InvestmentProductRequest> {
        return investmentProductRepository.findAll()
            .map {
                InvestmentProductRequest(
                    fundCode = it.fundCode,
                    investmentAmount = it.investmentAmount
                )
            }
    }

    fun saveAllInvestmentProducts() {
        val products = buildList {
            add(
                InvestmentProduct(
                    fundCode = "1000",
                    investmentAmount = 30000,
                    limitAmount = 3000000L
                )
            )
            add(
                InvestmentProduct(
                    fundCode = "1001",
                    investmentAmount = 30000,
                    limitAmount = 3000000L
                )
            )
            add(
                InvestmentProduct(
                    fundCode = "1002",
                    investmentAmount = 30000,
                    limitAmount = 3000000L
                )
            )
        }.toList()

        investmentProductRepository.saveAll(products)
    }
}
