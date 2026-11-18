package com.krimson.kuona.core.data.dao

import androidx.room.*
import com.krimson.kuona.core.data.db.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Insert suspend fun insert(device: DeviceEntity): Long
    @Insert suspend fun insertAll(devices: List<DeviceEntity>): List<Long>
    @Query("SELECT * FROM devices WHERE scanId = :scanId") fun observeByScan(scanId: Long): Flow<List<DeviceEntity>>
    @Query("SELECT * FROM devices WHERE scanId = :scanId") suspend fun getByScan(scanId: Long): List<DeviceEntity>
    @Query("SELECT * FROM devices WHERE id = :id") suspend fun getById(id: Long): DeviceEntity?
    @Query("DELETE FROM devices WHERE scanId = :scanId") suspend fun deleteByScan(scanId: Long)
}
