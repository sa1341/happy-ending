package com.kakaopaysec.happyending.logging.servlet

import jakarta.servlet.ServletOutputStream
import jakarta.servlet.WriteListener
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponseWrapper
import org.apache.commons.io.output.ByteArrayOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter

class OutputStreamCopiedHttpResponseWrapper(
    response: HttpServletResponse,
) : HttpServletResponseWrapper(response) {
    private val outputStream: ServletOutputStream = response.outputStream
    private val copier: OutputStreamCopier = OutputStreamCopier(outputStream)
    private lateinit var writer: PrintWriter

    override fun getOutputStream(): ServletOutputStream {
        return copier
    }

    override fun getWriter(): PrintWriter {
        writer = PrintWriter(OutputStreamWriter(copier, response.characterEncoding), true)
        return writer
    }

    override fun flushBuffer() {
        copier.flush()
    }

    val copy: ByteArray
        get() = copier.getCopy()
}

internal class OutputStreamCopier(
    private val outputStream: OutputStream?,
) : ServletOutputStream() {

    private val copy: ByteArrayOutputStream = ByteArrayOutputStream(1024)

    override fun write(b: Int) {
        outputStream!!.write(b)
        copy.write(b)
    }

    fun getCopy(): ByteArray {
        return copy.toByteArray()
    }

    override fun isReady(): Boolean {
        return false
    }

    override fun setWriteListener(listener: WriteListener) {
        // WriteListener 를 사용하지 않는다.
    }
}
