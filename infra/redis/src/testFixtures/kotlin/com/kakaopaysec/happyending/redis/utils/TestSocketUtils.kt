package com.kakaopaysec.happyending.redis.utils

import java.net.InetAddress
import java.net.ServerSocket
import javax.net.ServerSocketFactory

object TestSocketUtils {
    private const val PORT_RANGE_MIN = 1024
    private const val PORT_RANGE_MAX = 65535
    private const val MAX_ATTEMPTS = 1000

    fun findAvailableTcpPort(): Int {
        var candidatePort: Int
        var searchCounter = 0
        do {
            check(searchCounter <= MAX_ATTEMPTS) {
                String.format(
                    "Could not find an available TCP port in the range [%d, %d] after %d attempts",
                    PORT_RANGE_MIN,
                    PORT_RANGE_MAX,
                    MAX_ATTEMPTS
                )
            }
            candidatePort = (PORT_RANGE_MIN..PORT_RANGE_MAX).random()
            searchCounter++
        } while (!isPortAvailable(candidatePort))
        return candidatePort
    }

    /**
     * Determine if the specified TCP port is currently available on `localhost`.
     */
    private fun isPortAvailable(port: Int): Boolean {
        return try {
            val serverSocket: ServerSocket = ServerSocketFactory.getDefault().createServerSocket(
                port,
                1,
                InetAddress.getByName("localhost")
            )
            serverSocket.close()
            true
        } catch (ex: Exception) {
            false
        }
    }
}
