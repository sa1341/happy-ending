package com.kakaopaysec.happyending.batch.configuration

import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.boot.autoconfigure.batch.BatchProperties
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(BatchProperties::class)
class HappyEndingBatchConfiguration {

    /* spring batch 5부터 @EnableBatchProcessing 어노테이션 생략이 가능하지만,
     * 해당 어노테이션을 선언하는 경우 jobLauncherApplicationRunner 빈을 수동으로 등록이 필요하여 하기에 정의하였습니다.
     * BatchAutoConfiguration에 @ConditionalOnMissingBean이 추가되어있음
    */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.batch.job", name = ["enabled"], havingValue = "true", matchIfMissing = true)
    fun jobLauncherApplicationRunner(
        jobLauncher: JobLauncher,
        jobExplorer: JobExplorer,
        jobRepository: JobRepository,
        properties: BatchProperties,
    ): JobLauncherApplicationRunner {
        val runner = JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository)
        val jobName: String? = properties.job.name
        if (!jobName.isNullOrEmpty()) {
            runner.setJobName(jobName)
        }
        return runner
    }
}
