package com.doradosystems.mis.worker.processor;

import static com.codahale.metrics.MetricRegistry.name;
import static com.doradosystem.messaging.MessageExchangeQualifiers.INPUT_EVENT_BUS;
import static com.doradosystems.mis.worker.WorkerQualifiers.JOB_QUEUE_COUNTER;

import java.util.concurrent.ExecutorService;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.doradosystems.messaging.input.InputChannel;
import com.doradosystems.messaging.output.OutputChannel;
import com.google.common.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageProcessorModule {
  
  @Provides @Named(JOB_QUEUE_COUNTER)
  @Singleton
  @Nonnull
  public Counter provideJobQueueCounter(MetricRegistry metrics) {
    return metrics.counter(name(MessageProcessorModule.class, JOB_QUEUE_COUNTER));
  }
  
  @Provides
  @Singleton
  @Nonnull
  public InitializationStatus provideInitializationStatus(ExecutorService executorService, 
      MessageProcessor messageProcessor, 
      @Named(INPUT_EVENT_BUS) EventBus eventBus,
      OutputChannel outputChannel,
      InputChannel inputChannel) {
    eventBus.register(messageProcessor); 
    return InitializationStatus.COMPLETED;
  }

  
  public static enum InitializationStatus {
    COMPLETED, FAILED
  }
  
}
