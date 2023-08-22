package com.kakaopaysec.happyending.logging.servlet

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

class BodyFetchedHttpRequestWrapper(
    request: HttpServletRequest?
) : HttpServletRequestWrapper(request) {
    private val bodyData: ByteArray

    init {
        val inputStream = super.getInputStream()
        bodyData = IOUtils.toByteArray(inputStream)
    }

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        return SimpleServletInputStream(ByteArrayInputStream(bodyData))
    }
}

internal class SimpleServletInputStream(private val inputStream: InputStream) : ServletInputStream() {

    override fun read(): Int {
        return inputStream.read()
    }

    override fun read(b: ByteArray): Int {
        return inputStream.read(b)
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun isReady(): Boolean {
        return false
    }

    override fun setReadListener(readListener: ReadListener) {
        // ReadListener 를 사용하지 않는다.
    }
}
