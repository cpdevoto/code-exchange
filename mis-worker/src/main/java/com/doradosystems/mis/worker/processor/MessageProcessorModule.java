package com.doradosystems.mis.worker.processor;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.doradosystems.messaging.input.InputChannel;
import com.doradosystems.messaging.output.OutputChannel;
import com.doradosystems.mis.worker.config.WorkerConfiguration;
import com.google.common.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageProcessorModule {
  
  @Provides @Named("job-queue")
  @Singleton
  @Nonnull
  public BlockingQueue<Runnable> provideJobQueue(WorkerConfiguration config) {
    return new LinkedBlockingDeque<>(config.getMaxJobsInQueue());
  }
  
  @Provides @Named("job-queue-counter")
  @Singleton
  @Nonnull
  public Counter provideJobQueueCounter(MetricRegistry metrics) {
    return metrics.counter(name(MessageProcessorModule.class, "job-queue-counter"));
  }
  
  @Provides
  @Singleton
  @Nonnull
  public InitializationStatus provideInitializationStatus(ExecutorService executorService, 
      MessageProcessor messageProcessor, 
      @Named("input-channel-event-bus") EventBus eventBus,
      OutputChannel outputChannel,
      InputChannel inputChannel) {
    eventBus.register(messageProcessor); 
    return InitializationStatus.COMPLETED;
  }

  
  public static enum InitializationStatus {
    COMPLETED, FAILED
  }
  
}
