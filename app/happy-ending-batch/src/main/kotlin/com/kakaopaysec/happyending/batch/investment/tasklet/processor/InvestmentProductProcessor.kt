package com.kakaopaysec.happyending.batch.investment.tasklet.processor

import com.kakaopaysec.happyending.batch.investment.service.InvestmentProductService
import com.kakaopaysec.happyending.service.InvestmentProductRequest
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class InvestmentProductProcessor(
    private val investmentProductService: InvestmentProductService
) : ItemProcessor<InvestmentProductRequest, InvestmentProductRequest> {

    override fun process(item: InvestmentProductRequest): InvestmentProductRequest? {
        runCatching {
            investmentProductService.callApi(
                investmentProductRequest = com.kakaopaysec.happyending.batch.investment.model.InvestmentProductRequest(
                    fundCode = item.fundCode,
                    investmentAmount = item.investmentAmount
                )
            )
        }.onFailure {
            println(it)
        }.getOrThrow()

        return item
    }
}
