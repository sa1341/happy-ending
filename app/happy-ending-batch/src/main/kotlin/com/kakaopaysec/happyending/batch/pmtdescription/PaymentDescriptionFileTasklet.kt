package com.kakaopaysec.happyending.batch.pmtdescription

import com.kakaopaysec.happyending.utils.ProfileUtils
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@StepScope
class PaymentDescriptionFileTasklet(
    private val environment: Environment,
    private val profileUtils: ProfileUtils,
) : Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val profile = profileUtils.getActiveProfile()
        val baseYear = getJobParameter()
        println("##### BASE_YEAR ##### = $baseYear")
        return RepeatStatus.FINISHED
    }

    fun getJobParameter(): String {
        val defaultBaseYear = LocalDate.now().minusYears(1).year
        return environment.getProperty("base_year", String::class.java) ?: defaultBaseYear.toString()
    }
}
