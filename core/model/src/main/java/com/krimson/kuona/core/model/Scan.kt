package com.krimson.kuona.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Scan(
    val id: Long = 0,
    val startedAt: Long = System.currentTimeMillis(),
    val endedAt: Long? = null,
    val mode: ScanMode = ScanMode.SIMULATION,
    val networkName: String = ""
)
