package com.doradosystems.mis.scheduler;

import javax.inject.Singleton;

import org.devoware.homonculus.database.DatabaseConfigurationModule;
import org.devoware.homonculus.messaging.MessageExchangeModule;
import org.devoware.homonculus.messaging.output.OutputChannelModule;

import com.doradosystems.mis.scheduler.config.ConfigurationModule;
import com.doradosystems.mis.scheduler.dao.DaoModule;
import com.doradosystems.mis.scheduler.scheduling.SchedulingModule;
import com.doradosystems.mis.scheduler.scheduling.job.JobModule;
import com.doradosystems.mis.scheduler.setup.EnvironmentModule;

import dagger.Component;

@Singleton
@Component(modules = {
    ConfigurationModule.class, 
    DatabaseConfigurationModule.class, 
    EnvironmentModule.class, 
    DaoModule.class,
    MessageExchangeModule.class,
    OutputChannelModule.class,
    SchedulingModule.class,
    JobModule.class})
public interface MisSchedulerComponent {
  
  JobModule.InitializationStatus initialize();

}
