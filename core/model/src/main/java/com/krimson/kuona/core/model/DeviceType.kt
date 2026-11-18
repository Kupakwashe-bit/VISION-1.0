package com.krimson.kuona.core.model

import kotlinx.serialization.Serializable

@Serializable
enum class DeviceType { CORE, SWITCH, HOST, SERVER, ACCESS_POINT, HOTSPOT, BLUETOOTH, UNKNOWN }
