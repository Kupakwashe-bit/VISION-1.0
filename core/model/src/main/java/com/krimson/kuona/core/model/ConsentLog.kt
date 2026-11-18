package com.krimson.kuona.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ConsentLog(
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val text: String = ""
)
