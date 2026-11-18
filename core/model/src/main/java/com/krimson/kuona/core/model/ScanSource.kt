package com.krimson.kuona.core.model

import kotlinx.coroutines.flow.Flow

interface ScanSource {
    val sourceType: ScanSourceType
    val isAvailable: Boolean
    fun discover(scanId: Long): Flow<ScanEvent>
    suspend fun stop()
}
