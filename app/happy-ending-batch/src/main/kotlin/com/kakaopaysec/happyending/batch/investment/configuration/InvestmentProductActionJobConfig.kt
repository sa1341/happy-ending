package com.kakaopaysec.happyending.batch.investment.configuration

import com.kakaopaysec.happyending.batch.configuration.UniqueRunIdIncrementer
import com.kakaopaysec.happyending.batch.investment.tasklet.processor.InvestmentProductProcessor
import com.kakaopaysec.happyending.service.InvestmentProductRequest
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import java.sql.ResultSet
import javax.sql.DataSource

@Component
class InvestmentProductActionJobConfig(
    private val dataSource: DataSource
) {

    private val chunkSize = 3

    @Bean
    fun investmentProductActionJob(
        jobRepository: JobRepository,
        investmentProductActionStep: Step
    ): Job = JobBuilder(jobName, jobRepository)
        .start(investmentProductActionStep)
        .incrementer(UniqueRunIdIncrementer())
        .build()

    @Bean
    fun investmentProductActionStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        investmentProductProcessor: InvestmentProductProcessor,
    ): Step = StepBuilder(stepName, jobRepository)
        .chunk<InvestmentProductRequest, InvestmentProductRequest>(chunkSize, transactionManager)
        .reader(jdbcCursorItemReader())
        .processor(investmentProductProcessor)
        .writer(jdbcCursorItemWriter())
        .faultTolerant()
        .skipPolicy(AlwaysSkipItemSkipPolicy())
        .build()

    @Bean
    fun jdbcCursorItemReader(): JdbcCursorItemReader<InvestmentProductRequest> {
        val rowMapper: RowMapper<InvestmentProductRequest> = RowMapper { resultSet: ResultSet, _: Int ->
            InvestmentProductRequest(
                fundCode = resultSet.getString("fund_code"),
                investmentAmount = resultSet.getLong("investment_amount")
            )
        }
        return JdbcCursorItemReaderBuilder<InvestmentProductRequest>()
            .fetchSize(chunkSize)
            .dataSource(dataSource)
            .rowMapper(rowMapper)
            .sql(
                "SELECT investment_product_id, fund_code, investment_amount, limit_amount, created_at, created_by, updated_at, updated_by FROM investment_product" // ktlint-disable max-line-length
            )
            .name("jdbcCursorItemReader")
            .build()
    }

    @Bean
    fun jdbcCursorItemWriter(): ItemWriter<InvestmentProductRequest> {
        return object : ItemWriter<InvestmentProductRequest> {
            override fun write(chunk: Chunk<out InvestmentProductRequest>) {
                chunk.items.forEach {
                    println(it.fundCode)
                }
            }
        }
    }

    companion object {
        const val jobName = "investmentProductActionJob"
        const val stepName = "investmentProductActionStep"
    }
}
