package com.doradosystems.mis.worker.processor.job.claims;

import com.doradosystems.mis.agent.model.messaging.MessageType;
import com.doradosystems.mis.worker.processor.job.JobFactory;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class ClaimRetrievalJobModule {

  @Provides @IntoMap
  @StringKey(MessageType.CLAIM_RETRIEVAL)
  public static JobFactory provideClaimRetrievalJobFactory(ClaimRetrievalJobFactory factory) {
    return factory;
  }
  
}
