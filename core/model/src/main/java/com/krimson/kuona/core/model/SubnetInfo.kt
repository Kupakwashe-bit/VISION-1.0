package com.krimson.kuona.core.model

import kotlinx.serialization.Serializable

@Serializable
data class SubnetInfo(
    val index: Int,
    val label: String,
    val hostCount: Int,
    val flaggedCount: Int
)
