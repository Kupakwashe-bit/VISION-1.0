package com.krimson.kuona.core.network.source;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SimulationDataSource_Factory implements Factory<SimulationDataSource> {
  @Override
  public SimulationDataSource get() {
    return newInstance();
  }

  public static SimulationDataSource_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SimulationDataSource newInstance() {
    return new SimulationDataSource();
  }

  private static final class InstanceHolder {
    private static final SimulationDataSource_Factory INSTANCE = new SimulationDataSource_Factory();
  }
}
