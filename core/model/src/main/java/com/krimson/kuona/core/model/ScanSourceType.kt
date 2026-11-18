package com.krimson.kuona.core.model

import kotlinx.serialization.Serializable

@Serializable
enum class ScanSourceType { LAN, HOTSPOT_CLIENT, WIFI_AP, BLE, BT_CLASSIC, SIMULATION, IMPORT }
