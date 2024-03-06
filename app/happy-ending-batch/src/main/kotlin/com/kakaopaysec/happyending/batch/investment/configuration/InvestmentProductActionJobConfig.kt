package com.kakaopaysec.happyending.batch.investment.configuration

import com.kakaopaysec.happyending.batch.configuration.UniqueRunIdIncrementer
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager

@Component
class InvestmentProductActionJobConfig {

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
        transactionManager: PlatformTransactionManager
    ): Step = StepBuilder(stepName, jobRepository)
        .tasklet({ _, _ ->
            RepeatStatus.FINISHED
        }, transactionManager)
        .build()

    companion object {
        const val jobName = "investmentProductActionJob"
        const val stepName = "investmentProductActionStep"
    }
}
