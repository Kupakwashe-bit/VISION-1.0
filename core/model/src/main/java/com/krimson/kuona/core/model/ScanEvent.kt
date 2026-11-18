package com.krimson.kuona.core.model

sealed interface ScanEvent {
    data class StateChanged(val newState: ScanState) : ScanEvent
    data class DeviceDiscovered(val device: Device) : ScanEvent
    data class ThreatRevealed(val finding: Finding, val device: Device) : ScanEvent
    data class ScanProgress(val label: String, val subLabel: String) : ScanEvent
    data class ThreatCount(val count: Int, val total: Int) : ScanEvent
    data object ScanComplete : ScanEvent
    data class SubnetUpdated(val subnets: List<SubnetInfo>) : ScanEvent
}
