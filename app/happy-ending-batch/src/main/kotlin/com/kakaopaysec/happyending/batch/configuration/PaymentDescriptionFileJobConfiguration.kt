package com.kakaopaysec.happyending.batch.configuration

import com.kakaopaysec.happyending.batch.pmtdescription.PaymentDescriptionFileTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class PaymentDescriptionFileJobConfiguration {

    @Bean
    fun paymentDescriptionFileJob(
        jobRepository: JobRepository,
        paymentDescriptionFileStep: Step
    ): Job = JobBuilder(jobName, jobRepository)
        .start(paymentDescriptionFileStep)
        .incrementer(UniqueRunIdIncrementer())
        .build()

    @Bean
    @JobScope
    fun paymentDescriptionFileStep(
        paymentDescriptionFileTasklet: PaymentDescriptionFileTasklet,
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager
    ): Step = StepBuilder(stepName, jobRepository)
        .tasklet(paymentDescriptionFileTasklet, transactionManager)
        .build()
    companion object {
        const val jobName = "paymentDescriptionFileJob"
        const val stepName = "paymentDescriptionFileStep"
    }
}
