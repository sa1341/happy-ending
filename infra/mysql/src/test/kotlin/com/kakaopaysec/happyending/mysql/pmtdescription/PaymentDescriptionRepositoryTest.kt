package com.kakaopaysec.happyending.mysql.pmtdescription

import com.kakaopaysec.happyending.mysql.faker.PaymentDescriptionEntityFaker
import com.kakaopaysec.happyending.mysql.pmtdescription.model.type.RecordType
import com.kakaopaysec.happyending.mysql.pmtdescription.repository.CRecordRepository
import com.kakaopaysec.happyending.mysql.pmtdescription.repository.DRecordRepository
import com.kakaopaysec.happyending.mysql.pmtdescription.repository.FinalRecordRepository
import com.kakaopaysec.happyending.mysql.pmtdescription.repository.GRecordRepository
import com.kakaopaysec.happyending.mysql.support.DataJpaTestSupport
import com.kakaopaysec.happyending.utils.MediaCode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@ActiveProfiles("test")
class PaymentDescriptionRepositoryTest @Autowired constructor(
    private val cRecordRepository: CRecordRepository,
    private val dRecordRepository: DRecordRepository,
    private val gRecordRepository: GRecordRepository,
    private val finalRecordRepository: FinalRecordRepository,
) : DataJpaTestSupport() {

    @Test
    fun `지급명세서 C레코드를 저장한다`() {
        val actual = cRecordRepository.save(PaymentDescriptionEntityFaker.cRecordEntity())

        actual shouldNotBe null
        actual.also {
            it.accountNumber shouldBe "02000155683"
            it.tradeAt shouldBe "20220210"
            it.pnsaOthAcpTadA shouldBe BigDecimal("2000000")
        }
    }

    @Test
    fun `지급명세서 C레코드를 조회한다`() {
        cRecordRepository.save(PaymentDescriptionEntityFaker.cRecordEntity())
        val actual = cRecordRepository.findByAccountNumberAndTradeAtAndTradeSeqAndTxnSeq(
            accountNumber = "02000155683",
            tradeAt = "20220210",
            tradeSeq = 34499646L,
            txnSeq = 1L
        )

        actual shouldNotBe null
        actual?.also {
            it.accountNumber shouldBe "02000155683"
            it.tradeAt shouldBe "20220210"
            it.tradeSeq shouldBe 34499646L
            it.txnSeq shouldBe 1L
            it.pnsaOthAcpTadA shouldBe BigDecimal("2000000")
        }
    }

    @Test
    fun `지급명세서 D레코드를 저장한다`() {
        val dRecord = dRecordRepository.save(PaymentDescriptionEntityFaker.dRecordEntity())

        dRecord shouldNotBe null
        dRecord.also {
            it.accountNumber shouldBe "02000155683"
            it.baseYear shouldBe "2023"
            it.orErnBefEotBa shouldBe BigDecimal("10036857")
        }
    }

    @Test
    fun `지급명세서 D레코드를 조회한다`() {
        dRecordRepository.save(PaymentDescriptionEntityFaker.dRecordEntity())
        val actual = dRecordRepository.findByAccountNumberAndBaseYearAndCreatedAtAndCreatedSeq(
            accountNumber = "02000155683",
            baseYear = "2023",
            createdAt = "20230227",
            createdSeq = 1L
        )

        actual shouldNotBe null
        actual?.also {
            it.accountNumber shouldBe "02000155683"
            it.baseYear shouldBe "2023"
            it.orErnBefEotBa shouldBe BigDecimal("10036857")
        }
    }

    @Test
    fun `지급명세서 G레코드를 저장한다`() {
        val gRecord = gRecordRepository.save(PaymentDescriptionEntityFaker.gRecordEntity())

        gRecord shouldNotBe null
        gRecord.also {
            it.accountNumber shouldBe "02000168462"
            it.dfrRtmtIcmA shouldBe BigDecimal("20900000")
        }
    }

    @Test
    fun `지급명세서 G레코드를 조회한다`() {
        gRecordRepository.save(PaymentDescriptionEntityFaker.gRecordEntity())
        val actual = gRecordRepository.findByTradeAtAndTradeSeqAndAccountNumber(
            tradeAt = "20221115",
            tradeSeq = 2236964298L,
            accountNumber = "02000168462"
        )

        actual shouldNotBe null
        actual?.also {
            it.accountNumber shouldBe "02000168462"
            it.dfrRtmtIcmA shouldBe BigDecimal("20900000")
        }
    }

    @Test
    fun `지급명세서 final 레코드를 저장한다`() {
        val finalRecord = finalRecordRepository.save(PaymentDescriptionEntityFaker.finalRecordEntity())

        finalRecord shouldNotBe null
        finalRecord.also {
            it.accountNumber shouldBe "02000168462"
            it.icmImpdYr shouldBe "2023"
            it.recordType shouldBe RecordType.C
            it.requestTerminalType shouldBe MediaCode.INTEGRATED_TERMINAL
            it.createdBy shouldBe "jean.calm"
        }
    }

    @Test
    fun `지급명세서 final 레코드를 조회한다`() {
        finalRecordRepository.save(PaymentDescriptionEntityFaker.finalRecordEntity())
        val finalRecord = finalRecordRepository.findByIcmImpdYrAndRecordType(
            icmImpdYr = "2023",
            recordType = RecordType.C
        )

        finalRecord shouldNotBe null
        finalRecord?.also {
            it.accountNumber shouldBe "02000168462"
            it.icmImpdYr shouldBe "2023"
            it.recordType shouldBe RecordType.C
            it.requestTerminalType shouldBe MediaCode.INTEGRATED_TERMINAL
            it.createdBy shouldBe "jean.calm"
        }
    }
}
