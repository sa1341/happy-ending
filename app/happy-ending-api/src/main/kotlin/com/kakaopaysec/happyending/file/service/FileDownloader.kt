package com.kakaopaysec.happyending.file.service

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import java.io.FileNotFoundException
import java.nio.file.Paths

private val log = KotlinLogging.logger {}

@Service
class FileDownloader(
    @Value("\${happy-ending.file.path}")
    private val path: String
) {
    fun download(fileName: String): Resource {
        runCatching {
            val filePath = Paths.get(path).resolve(fileName).normalize()
            log.info { "filePath: $filePath" }
            val resource = UrlResource(filePath.toUri())

            if (resource.exists()) {
                return resource
            } else {
                throw FileNotFoundException("File not found: $fileName")
            }
        }.getOrElse {
            throw FileNotFoundException("File not found: $fileName")
        }
    }
}
