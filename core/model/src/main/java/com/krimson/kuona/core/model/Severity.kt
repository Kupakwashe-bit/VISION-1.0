package com.krimson.kuona.core.model

object Severity {
    const val INFO = 1
    const val LOW = 2
    const val MEDIUM = 3
    const val HIGH = 4
    const val CRITICAL = 5

    fun label(level: Int): String = when (level) {
        INFO -> "INFO"
        LOW -> "LOW"
        MEDIUM -> "MEDIUM"
        HIGH -> "HIGH"
        CRITICAL -> "CRITICAL"
        else -> "UNKNOWN"
    }
}
