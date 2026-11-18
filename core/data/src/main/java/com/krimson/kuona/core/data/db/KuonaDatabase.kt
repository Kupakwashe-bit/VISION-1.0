package com.krimson.kuona.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krimson.kuona.core.data.dao.*

@Database(
    entities = [
        ScanEntity::class,
        DeviceEntity::class,
        FlowEntity::class,
        FindingEntity::class,
        ConsentLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class KuonaDatabase : RoomDatabase() {
    abstract fun scanDao(): ScanDao
    abstract fun deviceDao(): DeviceDao
    abstract fun flowDao(): FlowDao
    abstract fun findingDao(): FindingDao
    abstract fun consentLogDao(): ConsentLogDao
}
