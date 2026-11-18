package com.krimson.kuona.core.data.dao

import androidx.room.*
import com.krimson.kuona.core.data.db.FlowEntity

@Dao
interface FlowDao {
    @Insert suspend fun insert(flow: FlowEntity): Long
    @Insert suspend fun insertAll(flows: List<FlowEntity>): List<Long>
    @Query("SELECT * FROM flows WHERE scanId = :scanId") suspend fun getByScan(scanId: Long): List<FlowEntity>
    @Query("DELETE FROM flows WHERE scanId = :scanId") suspend fun deleteByScan(scanId: Long)
}
