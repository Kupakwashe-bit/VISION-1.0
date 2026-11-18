package com.krimson.kuona.core.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.krimson.kuona.core.model.*

@Entity(
    tableName = "devices",
    foreignKeys = [ForeignKey(entity = ScanEntity::class, parentColumns = ["id"], childColumns = ["scanId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("scanId")]
)
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val scanId: Long,
    val source: ScanSourceType,
    val ip: String?,
    val mac: String?,
    val bssid: String?,
    val ssid: String?,
    val vendor: String?,
    val hostname: String?,
    val type: DeviceType,
    val subnetIndex: Int?,
    val rssi: Int?,
    val distanceBand: DistanceBand?,
    val firstSeen: Long,
    val lastSeen: Long,
    val posX: Float,
    val posY: Float,
    val posZ: Float
)
