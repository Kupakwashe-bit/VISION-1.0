package com.krimson.kuona.core.model

import kotlinx.serialization.Serializable

@Serializable
enum class ThreatType {
    PORT_SCAN, BRUTE_FORCE, C2_BEACON, DATA_EXFIL, ARP_SPOOF,
    LATERAL_MOVE, ROGUE_DEVICE, OPEN_WIFI, EVIL_TWIN, BLE_TRACKER,
    ANOMALY
}
