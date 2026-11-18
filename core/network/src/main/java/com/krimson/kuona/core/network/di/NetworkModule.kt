package com.krimson.kuona.core.network.di

import com.krimson.kuona.core.model.ScanSource
import com.krimson.kuona.core.network.socket.LocalOnlySocketFactory
import com.krimson.kuona.core.network.source.SimulationDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindSimulationDataSource(impl: SimulationDataSource): ScanSource

    companion object {
        @Provides
        @Singleton
        fun provideLocalOnlySocketFactory(): LocalOnlySocketFactory = LocalOnlySocketFactory()
    }
}
