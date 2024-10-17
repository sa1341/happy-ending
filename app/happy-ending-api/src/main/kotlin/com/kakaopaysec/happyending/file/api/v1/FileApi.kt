package com.kakaopaysec.happyending.file.api.v1

import com.kakaopaysec.happyending.file.service.FileDownloader
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.apache.commons.io.FileUtils
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File

private val log = KotlinLogging.logger {}

@RequestMapping("/api/v1/files")
@RestController
class FileApi(
    private val fileDownloader: FileDownloader,
) {

    @GetMapping
    fun downloadFile(
        @RequestParam("fileName") fileName: String,
        httpServletResponse: HttpServletResponse,
    ): ResponseEntity<Resource> {
        log.info { "fileName: $fileName" }
        val resource = fileDownloader.download(fileName)
        val contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(CONTENT_DISPOSITION, "attachment; filename=\"${resource.filename}\"")
            .body(resource)
    }

    @GetMapping("/binary")
    fun downloadByteArrays(
        @RequestParam("fileName") fileName: String,
        httpServletResponse: HttpServletResponse,
    ) {
        log.info { "fileName: $fileName" }
        val files = FileUtils.readFileToByteArray(File("/Users/limjun-young/workspace/privacy/dev/test/plaintext.txt"))
        val contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE

        httpServletResponse.contentType = contentType
        httpServletResponse.setContentLength(files.size)
        httpServletResponse.setHeader(CONTENT_DISPOSITION, "attachment; filename=\"plain.txt\"")
        httpServletResponse.setHeader("Content-Transfer-Encoding", "binary")

        httpServletResponse.outputStream.write(files)
        httpServletResponse.outputStream.flush()
        httpServletResponse.outputStream.close()
    }
}
