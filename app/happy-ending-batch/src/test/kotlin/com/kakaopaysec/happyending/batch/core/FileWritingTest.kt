package com.kakaopaysec.happyending.batch.core

import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.Charset

class FileWritingTest {

    @Test
    fun `euc-kr로 파일을 작성한다`() {
        // given
        val filePath = "/Users/limjun-young/workspace/privacy/dev/test/euckr.txt"
        val text = "임준영"

        val eucKrText = String(text.toByteArray(Charset.forName("euc-kr")), Charset.forName("euc-kr"))
        println("euc-kr text: $eucKrText")

        // when
        val fos = FileOutputStream(filePath)
        val os = OutputStreamWriter(fos, Charset.forName("euc-kr"))
        val bw = BufferedWriter(os).use {
            it.write(eucKrText)
        }

        val fis = FileInputStream(filePath)
        val isr = InputStreamReader(fis, Charset.forName("euc-kr"))
        val br = BufferedReader(isr)
        val readLine = br.readLine()
        println(readLine)
        println(readLine.toByteArray().size)
    }
}
