package com.krimson.kuona.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Device(
    val id: Long = 0,
    val scanId: Long = 0,
    val source: ScanSourceType = ScanSourceType.SIMULATION,
    val ip: String? = null,
    val mac: String? = null,
    val bssid: String? = null,
    val ssid: String? = null,
    val vendor: String? = null,
    val hostname: String? = null,
    val type: DeviceType = DeviceType.UNKNOWN,
    val subnetIndex: Int? = null,
    val rssi: Int? = null,
    val distanceBand: DistanceBand? = null,
    val firstSeen: Long = System.currentTimeMillis(),
    val lastSeen: Long = System.currentTimeMillis(),
    val posX: Float = 0f,
    val posY: Float = 0f,
    val posZ: Float = 0f
)
