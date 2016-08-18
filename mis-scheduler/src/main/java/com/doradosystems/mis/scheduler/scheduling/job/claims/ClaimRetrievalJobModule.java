package com.doradosystems.mis.scheduler.scheduling.job.claims;

import org.quartz.Job;

import com.doradosystems.mis.agent.model.messaging.MessageType;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class ClaimRetrievalJobModule {

  @Provides @IntoMap
  @StringKey(MessageType.CLAIM_RETRIEVAL)
  public static Job provideClaimRetrievalJob(ClaimRetrievalJob job) {
    return job;
  }
  
  @Provides @IntoMap
  @StringKey(MessageType.CLAIM_RETRIEVAL)
  public static Class<? extends Job> provideClaimRetrievalJobClass() {
    return ClaimRetrievalJob.class;
  }
}
