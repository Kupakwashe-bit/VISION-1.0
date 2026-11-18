package com.krimson.kuona.core.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.krimson.kuona.core.model.*

@Entity(
    tableName = "findings",
    foreignKeys = [ForeignKey(entity = ScanEntity::class, parentColumns = ["id"], childColumns = ["scanId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("scanId"), Index("deviceId")]
)
data class FindingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val scanId: Long,
    val deviceId: Long,
    val type: ThreatType,
    val severity: Int,
    val score: Float,
    val evidence: List<String>,
    val status: FindingStatus,
    val firstSeen: Long,
    val lastSeen: Long,
    val explanationKey: String
)
