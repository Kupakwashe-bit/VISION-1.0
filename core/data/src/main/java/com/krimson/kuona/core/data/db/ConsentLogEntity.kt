package com.krimson.kuona.core.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consent_log")
data class ConsentLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val text: String
)
