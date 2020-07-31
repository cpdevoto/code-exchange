package com.doradosystems.mis.worker;

import javax.inject.Singleton;

import org.devoware.homonculus.database.DatabaseConfigurationModule;
import org.devoware.homonculus.messaging.MessageExchangeModule;
import org.devoware.homonculus.messaging.input.InputChannelModule;
import org.devoware.homonculus.messaging.output.OutputChannelModule;

import com.doradosystems.mis.worker.config.ConfigurationModule;
import com.doradosystems.mis.worker.dao.DaoModule;
import com.doradosystems.mis.worker.ddeservice.DdeServiceModule;
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
    JobModule.class,
    DdeServiceModule.class})
public interface MisWorkerComponent {
  
  MessageProcessorModule.InitializationStatus initialize();
}
