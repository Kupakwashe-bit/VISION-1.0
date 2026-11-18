package com.krimson.kuona.core.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.krimson.kuona.core.model.ScanMode

@Entity(tableName = "scans")
data class ScanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val startedAt: Long,
    val endedAt: Long?,
    val mode: ScanMode,
    val networkName: String
)
