package com.krimson.kuona.core.network.socket

import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import javax.net.SocketFactory

/**
 * Enforces the strict KUONA local-only invariant:
 * Refuses any connection targeting an address outside private / link-local / loopback ranges.
 * RFC 1918 (10.0.0.0/8, 172.16.0.0/12, 192.168.0.0/16),
 * 169.254.0.0/16 (link-local),
 * fc00::/7 (unique local IPv6),
 * fe80::/10 (link-local IPv6),
 * 127.0.0.0/8, ::1 (loopback).
 */
class LocalOnlySocketFactory : SocketFactory() {

    companion object {
        fun isLocalAddress(addr: InetAddress): Boolean {
            if (addr.isLoopbackAddress || addr.isLinkLocalAddress || addr.isSiteLocalAddress) {
                return true
            }
            val bytes = addr.address
            if (bytes.size == 4) {
                val b0 = bytes[0].toInt() and 0xFF
                val b1 = bytes[1].toInt() and 0xFF
                // 10.0.0.0/8
                if (b0 == 10) return true
                // 172.16.0.0/12
                if (b0 == 172 && (b1 in 16..31)) return true
                // 192.168.0.0/16
                if (b0 == 192 && b1 == 168) return true
                // 169.254.0.0/16
                if (b0 == 169 && b1 == 254) return true
                // 127.0.0.0/8
                if (b0 == 127) return true
                return false
            } else if (bytes.size == 16) {
                val b0 = bytes[0].toInt() and 0xFF
                // fc00::/7 (fc00 - fdff)
                if ((b0 and 0xFE) == 0xFC) return true
                // fe80::/10 (fe80 - febf)
                val b1 = bytes[1].toInt() and 0xFF
                if (b0 == 0xFE && (b1 and 0xC0) == 0x80) return true
                // ::1
                if (addr.isLoopbackAddress) return true
                return false
            }
            return false
        }

        fun checkAddress(addr: InetAddress) {
            if (!isLocalAddress(addr)) {
                throw SecurityException("KUONA LocalOnly policy violation: outbound connection to public IP \ blocked.")
            }
        }
    }

    override fun createSocket(): Socket = GuardedSocket()

    override fun createSocket(host: String, port: Int): Socket {
        val addr = InetAddress.getByName(host)
        checkAddress(addr)
        return Socket(addr, port)
    }

    override fun createSocket(host: String, port: Int, localHost: InetAddress, localPort: Int): Socket {
        val addr = InetAddress.getByName(host)
        checkAddress(addr)
        return Socket(addr, port, localHost, localPort)
    }

    override fun createSocket(host: InetAddress, port: Int): Socket {
        checkAddress(host)
        return Socket(host, port)
    }

    override fun createSocket(address: InetAddress, port: Int, localAddress: InetAddress, localPort: Int): Socket {
        checkAddress(address)
        return Socket(address, port, localAddress, localPort)
    }

    private class GuardedSocket : Socket() {
        override fun connect(endpoint: SocketAddress?) {
            verify(endpoint)
            super.connect(endpoint)
        }

        override fun connect(endpoint: SocketAddress?, timeout: Int) {
            verify(endpoint)
            super.connect(endpoint, timeout)
        }

        private fun verify(endpoint: SocketAddress?) {
            if (endpoint is InetSocketAddress) {
                checkAddress(endpoint.address)
            }
        }
    }
}

