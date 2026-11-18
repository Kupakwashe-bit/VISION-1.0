package com.krimson.kuona.core.data.db

import androidx.room.TypeConverter
import com.krimson.kuona.core.model.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter fun fromScanMode(v: ScanMode): String = v.name
    @TypeConverter fun toScanMode(v: String): ScanMode = ScanMode.valueOf(v)

    @TypeConverter fun fromDeviceType(v: DeviceType): String = v.name
    @TypeConverter fun toDeviceType(v: String): DeviceType = DeviceType.valueOf(v)

    @TypeConverter fun fromScanSourceType(v: ScanSourceType): String = v.name
    @TypeConverter fun toScanSourceType(v: String): ScanSourceType = ScanSourceType.valueOf(v)

    @TypeConverter fun fromDistanceBand(v: DistanceBand?): String? = v?.name
    @TypeConverter fun toDistanceBand(v: String?): DistanceBand? = v?.let { DistanceBand.valueOf(it) }

    @TypeConverter fun fromThreatType(v: ThreatType): String = v.name
    @TypeConverter fun toThreatType(v: String): ThreatType = ThreatType.valueOf(v)

    @TypeConverter fun fromFindingStatus(v: FindingStatus): String = v.name
    @TypeConverter fun toFindingStatus(v: String): FindingStatus = FindingStatus.valueOf(v)

    @TypeConverter fun fromStringList(v: List<String>): String = json.encodeToString(v)
    @TypeConverter fun toStringList(v: String): List<String> = json.decodeFromString(v)
}
