package com.krimson.kuona.core.data.dao

import androidx.room.*
import com.krimson.kuona.core.data.db.FindingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FindingDao {
    @Insert suspend fun insert(finding: FindingEntity): Long
    @Insert suspend fun insertAll(findings: List<FindingEntity>): List<Long>
    @Query("SELECT * FROM findings WHERE scanId = :scanId") fun observeByScan(scanId: Long): Flow<List<FindingEntity>>
    @Query("SELECT * FROM findings WHERE scanId = :scanId") suspend fun getByScan(scanId: Long): List<FindingEntity>
    @Query("SELECT * FROM findings WHERE deviceId = :deviceId") suspend fun getByDevice(deviceId: Long): List<FindingEntity>
    @Query("DELETE FROM findings WHERE scanId = :scanId") suspend fun deleteByScan(scanId: Long)
    @Query("DELETE FROM findings") suspend fun deleteAll()
}
