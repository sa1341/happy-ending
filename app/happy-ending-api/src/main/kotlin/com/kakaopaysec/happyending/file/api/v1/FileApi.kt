package com.kakaopaysec.happyending.file.api.v1

import com.kakaopaysec.happyending.file.service.FileDownloader
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RequestMapping("/api/v1/files")
@RestController
class FileApi(
    private val fileDownloader: FileDownloader
) {

    @GetMapping
    fun downloadFile(
        @RequestParam("fileName") fileName: String,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<Resource> {
        log.info { "fileName: $fileName" }
        val resource = fileDownloader.download(fileName)
        val contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${resource.filename}\"")
            .body(resource)
    }
}
