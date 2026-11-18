package com.krimson.kuona.core.model

sealed interface AudioEvent {
    data object Boot : AudioEvent
    data object ScanSweepStart : AudioEvent
    data object TypeTick : AudioEvent
    data object UiBlip : AudioEvent
    data class ThreatPing(val severity: Int) : AudioEvent
    data class ThreatsConfirmed(val count: Int) : AudioEvent
    data object Glitch : AudioEvent
    data object AmbientStart : AudioEvent
    data object AmbientStop : AudioEvent
}
