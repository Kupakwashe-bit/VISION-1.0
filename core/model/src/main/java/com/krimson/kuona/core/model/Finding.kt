package com.krimson.kuona.core.model

import kotlinx.serialization.Serializable

@Serializable
enum class FindingStatus { ACTIVE, ACKNOWLEDGED, RESOLVED }

@Serializable
data class Finding(
    val id: Long = 0,
    val scanId: Long = 0,
    val deviceId: Long = 0,
    val type: ThreatType = ThreatType.ANOMALY,
    val severity: Int = Severity.MEDIUM,
    val score: Float = 0f,
    val evidence: List<String> = emptyList(),
    val status: FindingStatus = FindingStatus.ACTIVE,
    val firstSeen: Long = System.currentTimeMillis(),
    val lastSeen: Long = System.currentTimeMillis(),
    val explanationKey: String = ""
)
