package com.krimson.kuona.core.network

import com.krimson.kuona.core.network.socket.LocalOnlySocketFactory
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.InetAddress

class LocalOnlySocketFactoryTest {

    @Test
    fun testPrivateIPv4_Allowed() {
        // 10.0.0.0/8
        assertTrue(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("10.0.0.1")))
        assertTrue(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("10.255.255.254")))
        // 172.16.0.0/12
        assertTrue(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("172.16.0.1")))
        assertTrue(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("172.31.255.254")))
        // 192.168.0.0/16
        assertTrue(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("192.168.1.1")))
        assertTrue(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("192.168.254.254")))
        // 169.254.0.0/16 link-local
        assertTrue(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("169.254.1.1")))
        // 127.0.0.1 loopback
        assertTrue(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("127.0.0.1")))
    }

    @Test
    fun testPublicIPv4_Blocked() {
        assertFalse(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("8.8.8.8")))
        assertFalse(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("1.1.1.1")))
        assertFalse(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("142.250.190.46"))) // google.com
        assertFalse(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("172.15.0.1"))) // just below 172.16
        assertFalse(LocalOnlySocketFactory.isLocalAddress(InetAddress.getByName("172.32.0.1"))) // just above 172.31
    }

    @Test(expected = SecurityException::class)
    fun testCheckAddressThrowsOnPublic() {
        LocalOnlySocketFactory.checkAddress(InetAddress.getByName("8.8.4.4"))
    }
}
