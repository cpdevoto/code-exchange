package com.doradosystems.mis.worker.processor.job;

import com.doradosystems.mis.agent.model.messaging.MessageType;
import com.doradosystems.mis.worker.processor.job.claims.ClaimRetrievalJobModule;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module(includes=ClaimRetrievalJobModule.class)
public class JobModule {

  @Provides @IntoMap
  @StringKey(MessageType.HEALTH_CHECK)
  public static JobFactory provideHealthCheckJobFactory() {
    return (message) -> null;
  }
}
