package com.krimson.kuona.core.network.di;

import com.krimson.kuona.core.network.socket.LocalOnlySocketFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class NetworkModule_Companion_ProvideLocalOnlySocketFactoryFactory implements Factory<LocalOnlySocketFactory> {
  @Override
  public LocalOnlySocketFactory get() {
    return provideLocalOnlySocketFactory();
  }

  public static NetworkModule_Companion_ProvideLocalOnlySocketFactoryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LocalOnlySocketFactory provideLocalOnlySocketFactory() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.Companion.provideLocalOnlySocketFactory());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_Companion_ProvideLocalOnlySocketFactoryFactory INSTANCE = new NetworkModule_Companion_ProvideLocalOnlySocketFactoryFactory();
  }
}
