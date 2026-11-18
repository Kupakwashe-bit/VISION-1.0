package com.krimson.kuona.core.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "flows",
    foreignKeys = [ForeignKey(entity = ScanEntity::class, parentColumns = ["id"], childColumns = ["scanId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("scanId")]
)
data class FlowEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val scanId: Long,
    val srcDeviceId: Long,
    val dstDeviceId: Long,
    val dstIp: String,
    val port: Int,
    val protocol: String,
    val bytesIn: Long,
    val bytesOut: Long,
    val startedAt: Long,
    val durationMs: Long
)
