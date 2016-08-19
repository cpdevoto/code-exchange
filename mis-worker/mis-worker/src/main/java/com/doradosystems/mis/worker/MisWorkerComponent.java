package com.doradosystems.mis.worker;

import javax.inject.Singleton;

import org.devoware.config.db.DatabaseConfigurationModule;

import com.doradosystem.messaging.MessageExchangeModule;
import com.doradosystems.messaging.input.InputChannelModule;
import com.doradosystems.messaging.output.OutputChannelModule;
import com.doradosystems.mis.worker.config.ConfigurationModule;
import com.doradosystems.mis.worker.dao.DaoModule;
import com.doradosystems.mis.worker.processor.MessageProcessorModule;
import com.doradosystems.mis.worker.processor.job.JobModule;
import com.doradosystems.mis.worker.setup.EnvironmentModule;

import dagger.Component;

@Singleton
@Component(modules = {
    ConfigurationModule.class, 
    DatabaseConfigurationModule.class, 
    EnvironmentModule.class, 
    DaoModule.class, 
    MessageExchangeModule.class,
    InputChannelModule.class,
    OutputChannelModule.class,
    MessageProcessorModule.class,
    JobModule.class})
public interface MisWorkerComponent {
  
  MessageProcessorModule.InitializationStatus initialize();
}