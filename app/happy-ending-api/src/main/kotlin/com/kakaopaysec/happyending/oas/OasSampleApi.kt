package com.kakaopaysec.happyending.oas

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/samples")
@RestController
class OasSampleApi {

    @GetMapping("/{sampleId}")
    fun getSampleById(
        @PathVariable sampleId: String,
    ): SampleResponse =
        SampleResponse(sampleId, "sample-$sampleId")
}

data class SampleResponse(
    val sampleId: String,
    val name: String,
)
