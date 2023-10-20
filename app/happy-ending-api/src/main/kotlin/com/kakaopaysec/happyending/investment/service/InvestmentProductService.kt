package com.kakaopaysec.happyending.investment.service

import com.kakaopaysec.happyending.account.service.AccountService
import com.kakaopaysec.happyending.annotation.DistributeRedissonLock
import com.kakaopaysec.happyending.investment.model.InvestmentProductRequest
import com.kakaopaysec.happyending.investment.model.InvestmentProductResponse
import com.kakaopaysec.happyending.mysql.fund.repository.FundProductRepository
import com.kakaopaysec.happyending.mysql.invest.entity.InvestmentProductHistory
import com.kakaopaysec.happyending.mysql.invest.repository.InvestmentProductHistoryRepository
import com.kakaopaysec.happyending.mysql.invest.repository.InvestmentProductRepository
import org.springframework.stereotype.Service

@Service
class InvestmentProductService(
    private val accountService: AccountService,
    private val fundProductRepository: FundProductRepository,
    private val investmentProductRepository: InvestmentProductRepository,
    private val investmentProductHistoryRepository: InvestmentProductHistoryRepository
) {

    @DistributeRedissonLock
    fun participateInvestmentEvent(
        accountNumber: String,
        request: InvestmentProductRequest
    ): InvestmentProductResponse {
        // 계좌조회
        val account = accountService.getAccount(accountNumber) ?: throw RuntimeException("계좌가 없습니다.")

        // 펀드상품 조회
        val fundProduct = fundProductRepository.findByCode(request.fundCode) ?: throw RuntimeException("펀드상품이 없습니다.")

        // 투자상품 조회
        val investmentProduct = investmentProductRepository.findByFundCode(fundProduct.code) ?: throw RuntimeException("투자상품이 없습니다.")

        // 투자상품 투자금액 갱신 (모집금액 한도를 넘어가면 예외 발생시킴)
        investmentProduct.updateInvestAmount(request.investmentAmount)
        val investmentProductHistory = investmentProductHistoryRepository.save(
            InvestmentProductHistory(
                accountNumber = account.accountNo,
                fundCode = fundProduct.code,
                investAmount = request.investmentAmount,
                investmentProductId = investmentProduct.id
            )
        )

        return InvestmentProductResponse(
            id = investmentProductHistory.id,
            isSuccess = true
        )
    }

    fun participateInvestment(
        accountNumber: String,
        request: InvestmentProductRequest
    ): InvestmentProductResponse {
        // 계좌조회
        val account = accountService.getAccount(accountNumber) ?: throw RuntimeException("계좌가 없습니다.")

        // 펀드상품 조회
        val fundProduct = fundProductRepository.findByCode(request.fundCode) ?: throw RuntimeException("펀드상품이 없습니다.")

        // 투자상품 조회
        val investmentProduct = investmentProductRepository.findByFundCode(fundProduct.code) ?: throw RuntimeException("투자상품이 없습니다.")

        // 투자상품 투자금액 갱신 (모집금액 한도를 넘어가면 예외 발생시킴)
        investmentProduct.updateInvestAmount(request.investmentAmount)
        val investmentProductHistory = investmentProductHistoryRepository.save(
            InvestmentProductHistory(
                accountNumber = account.accountNo,
                fundCode = fundProduct.code,
                investAmount = request.investmentAmount,
                investmentProductId = investmentProduct.id
            )
        )

        return InvestmentProductResponse(
            id = investmentProductHistory.id,
            isSuccess = true
        )
    }
}
