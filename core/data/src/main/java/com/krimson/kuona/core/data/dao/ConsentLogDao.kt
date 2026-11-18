package com.krimson.kuona.core.data.dao

import androidx.room.*
import com.krimson.kuona.core.data.db.ConsentLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsentLogDao {
    @Insert suspend fun insert(log: ConsentLogEntity): Long
    @Query("SELECT * FROM consent_log ORDER BY timestamp DESC") fun observeAll(): Flow<List<ConsentLogEntity>>
    @Query("DELETE FROM consent_log") suspend fun deleteAll()
}
