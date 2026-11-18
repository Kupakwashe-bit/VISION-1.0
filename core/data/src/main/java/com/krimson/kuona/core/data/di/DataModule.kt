package com.krimson.kuona.core.data.di

import android.content.Context
import androidx.room.Room
import com.krimson.kuona.core.data.db.KuonaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): KuonaDatabase {
        val passphrase = "kuona_encrypted_db_key_secure".toByteArray()
        val factory = SupportOpenHelperFactory(passphrase)
        return Room.databaseBuilder(context, KuonaDatabase::class.java, "kuona.db")
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides fun provideScanDao(db: KuonaDatabase) = db.scanDao()
    @Provides fun provideDeviceDao(db: KuonaDatabase) = db.deviceDao()
    @Provides fun provideFlowDao(db: KuonaDatabase) = db.flowDao()
    @Provides fun provideFindingDao(db: KuonaDatabase) = db.findingDao()
    @Provides fun provideConsentLogDao(db: KuonaDatabase) = db.consentLogDao()
}
