package com.krimson.kuona.core.data.dao

import androidx.room.*
import com.krimson.kuona.core.data.db.ScanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanDao {
    @Insert suspend fun insert(scan: ScanEntity): Long
    @Update suspend fun update(scan: ScanEntity)
    @Query("SELECT * FROM scans ORDER BY startedAt DESC") fun observeAll(): Flow<List<ScanEntity>>
    @Query("SELECT * FROM scans WHERE id = :id") suspend fun getById(id: Long): ScanEntity?
    @Query("DELETE FROM scans") suspend fun deleteAll()
}
