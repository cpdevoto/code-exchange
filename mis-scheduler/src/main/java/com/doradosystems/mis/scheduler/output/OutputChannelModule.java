package com.doradosystems.mis.scheduler.output;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeoutException;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.devoware.homonculus.lifecycle.Managed;
import org.devoware.homonculus.setup.Environment;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.doradosystems.mis.scheduler.config.RabbitMqConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class OutputChannelModule {

  @Provides
  @Singleton
  @Nonnull
  public OutputChannel providesOutputChannel(RabbitMqOutputChannel outputChannel, HealthCheckRegistry healthchecks) {
    OutputChannelHealthCheck healthcheck = new OutputChannelHealthCheck(outputChannel);
    healthchecks.register("outputChannel", healthcheck);
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
    try {
      ObjectName name = new ObjectName("healthchecks:type=OutputChannel"); 
      mbs.registerMBean(healthcheck, name);
    } catch (MalformedObjectNameException | InstanceAlreadyExistsException
        | MBeanRegistrationException | NotCompliantMBeanException e) {
      throw new OutputChannelInitializationException(
          "A problem occurred while attempting to create the output channel", e);
    } 
    return outputChannel;
  }
  
  @Provides
  @Singleton
  @Nonnull
  public Connection providesRabbitMqConnection (RabbitMqConfiguration config, Environment env) {
    final ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(config.getUser());
    factory.setPassword(config.getPassword());
    factory.setVirtualHost(config.getVirtualHost());
    factory.setHost(config.getHost());
    factory.setPort(config.getPort());
    try {
      final Connection connection = factory.newConnection();
      env.manage(new ConnectionManager(connection));
      return connection;
    } catch (IOException | TimeoutException e) {
      throw new OutputChannelInitializationException(
          "A problem occurred while attempting to create the RabbitMQ connection", e);
    }
  }
  
  @Provides
  @Singleton
  @Nonnull
  public Channel providesRabbitMqChannel (RabbitMqConfiguration config, Environment env, Connection connection) {
    // It's OK to declare the channel as a singleton, since it will only be accessed by a single thread at a time.
    // If this were a multi-threaded application, we would want to have one channel per thread (i.e. use a ThreadLocal)
    try {
      Channel channel = connection.createChannel();
      channel.queueDeclare(config.getQueue(), true, false, false, null);
      env.manage(new ChannelManager(channel));
      return channel;
    } catch (IOException e) {
      throw new OutputChannelInitializationException(
          "A problem occurred while attempting to create the RabbitMQ channel", e);
    }
  }
  
  
  private static class ConnectionManager implements Managed {
    private Connection connection;
    
    ConnectionManager(@Nonnull Connection connection) {
      this.connection = requireNonNull(connection);
    }
    
    @Override
    public void stop() throws IOException {
        connection.close();      
    }
    
    @Override
    public int getPriority() {
      return 998; // We want to make sure the connection is closed after the channel
    }
  }
  
  private static class ChannelManager implements Managed {
    private Channel channel;
    
    ChannelManager(@Nonnull Channel channel) {
      this.channel = requireNonNull(channel);
    }
    
    @Override
    public void stop() throws IOException, TimeoutException {
        channel.close();      
    }
    
  }
}
