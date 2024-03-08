package com.kakaopaysec.happyending

import com.kakaopaysec.happyending.mysql.invest.entity.InvestmentProduct
import com.kakaopaysec.happyending.mysql.invest.repository.InvestmentProductRepository
import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import kotlin.system.exitProcess

private val log = KotlinLogging.logger {}

@SpringBootApplication
class HappyEndingBatchApplication(
    private val investmentProductRepository: InvestmentProductRepository
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
     /*   (1..10).forEach {
            val investmentProduct = InvestmentProduct(
                fundCode = (1000 + it).toString(),
                investmentAmount = (30000 + it).toLong(),
                limitAmount = (30000 + it).toLong()
            )
            investmentProductRepository.save(investmentProduct)
        }*/
    }
}

fun main(args: Array<String>) {
    try {
        exitProcess(
            SpringApplication.exit(
                SpringApplicationBuilder(HappyEndingBatchApplication::class.java)
                    .web(WebApplicationType.NONE)
                    .run(*args)
            )
        )
    } catch (e: Throwable) {
        log.error("Spring Batch execution failed", e)
        exitProcess(1)
    }
}
