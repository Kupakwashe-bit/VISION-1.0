package com.krimson.kuona.core.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkFlow(
    val id: Long = 0,
    val scanId: Long = 0,
    val srcDeviceId: Long = 0,
    val dstDeviceId: Long = 0,
    val dstIp: String = "",
    val port: Int = 0,
    val protocol: String = "TCP",
    val bytesIn: Long = 0,
    val bytesOut: Long = 0,
    val startedAt: Long = System.currentTimeMillis(),
    val durationMs: Long = 0
)
