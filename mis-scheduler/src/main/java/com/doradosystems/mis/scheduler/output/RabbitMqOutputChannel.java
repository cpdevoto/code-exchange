package com.doradosystems.mis.scheduler.output;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.devoware.homonculus.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doradosystems.mis.scheduler.config.RabbitMqConfiguration;
import com.rabbitmq.client.Channel;

public class RabbitMqOutputChannel implements OutputChannel, Managed {
  Logger log = LoggerFactory.getLogger(RabbitMqOutputChannel.class);
  
  private Channel channel;
  private String exchange;
  private String queue;

  @Inject
  public RabbitMqOutputChannel(Channel channel, RabbitMqConfiguration config) {
    this.channel = channel;
    this.exchange = config.getExchange();
    this.queue = config.getQueue();
  }
  

  @Override
  public void write(@Nonnull String message) throws IOException {
      channel.basicPublish(exchange, queue, null, message.getBytes(StandardCharsets.UTF_8));
      if (log.isDebugEnabled()) {
        log.debug("Successfully published message: " + message);
      }
  }
}
