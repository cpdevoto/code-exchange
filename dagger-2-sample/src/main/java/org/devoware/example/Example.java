package org.devoware.example;

import java.io.IOException;

import javax.sql.DataSource;
import javax.validation.Validator;

import org.devoware.configuration.ClasspathConfigurationSourceProvider;
import org.devoware.configuration.ConfigurationException;
import org.devoware.configuration.ConfigurationFactory;
import org.devoware.configuration.ConfigurationSourceProvider;
import org.devoware.configuration.YamlConfigurationFactory;
import org.devoware.configuration.validation.Validators;
import org.devoware.example.config.ConfigurationModule;
import org.devoware.example.config.Dagger2SampleConfiguration;
import org.devoware.example.dao.UserDao;
import org.devoware.example.lifecycle.Managed;
import org.devoware.example.setup.Environment;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Example {

  public static void main(String[] args) throws Exception {
    // Enable Local JMX Monitoring and Management
    System.setProperty("com.sun.management.jmxremote", "true");
    
    // Load the configuration from the YAML file.
    ConfigurationFactory<Dagger2SampleConfiguration> factory = new YamlConfigurationFactory<>(Dagger2SampleConfiguration.class, new ObjectMapper(), Validators.newValidator());
    ConfigurationSourceProvider provider = new ClasspathConfigurationSourceProvider();
    Dagger2SampleConfiguration config = factory.build(provider, "dagger-2-sample.yml");
    
    ObjectMapper objectMapper = new ObjectMapper();
    Validator validator = Validators.newValidator();
    MetricRegistry metrics = new MetricRegistry();
    HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();
    Environment environment = new Environment(objectMapper, validator, metrics, healthCheckRegistry);
    
    //Instantiate the component
    Dagger2SampleComponent component = DaggerDagger2SampleComponent.builder()
        .configurationModule(new ConfigurationModule(config, environment))
        .build();
    
    //Use component getters to retrieve a data source and a dao
    DataSource dataSource = component.getDataSource();
    
    System.out.println(dataSource.getClass().getName());
    
    UserDao userDao = component.getUserDao();
    
    System.out.println(userDao.getUser(1));
    System.out.println(userDao.getDataSource().equals(dataSource));
    
    System.out.println("Running server...");
    for (Managed managed: environment.getManagedResources()) {
      managed.start();
    }
    while (true) {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException ex) {
        for (Managed managed: environment.getManagedResources()) {
          managed.stop();
        }
        throw ex;
      }
    }
  }

}
