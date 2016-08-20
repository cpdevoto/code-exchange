package com.doradosystems.mis.worker.processor.job;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doradosystems.messaging.input.Message;
import com.doradosystems.mis.agent.model.messaging.MessageHeaders;
import com.rabbitmq.client.BasicProperties;

public class Jobs {
  private static final Logger log = LoggerFactory.getLogger(Jobs.class);
  
  private final Map<String, JobFactory> factories;

  @Inject
  public Jobs(@Nonnull Map<String, JobFactory> factories) {
    this.factories = requireNonNull(factories);
  }

  @CheckForNull
  public Runnable createJob(@Nonnull Message message) throws JobCreationException {
    String messageType = getMessageType(message);
    if (messageType == null) {
      log.warn("Expected a message type within the message. The following message will be dropped: " + message);
      return null;
    }
    JobFactory factory = factories.get(messageType);
    if (factory == null) {
      log.warn("Unrecognized message type '" + messageType + "' for the message. The following message will be dropped: " + message);
      return null;
    }
    return factory.createJob(message);
  }

  @CheckForNull
  private String getMessageType(Message message) {
    String messageType = null;
    BasicProperties props = message.getProperties();
    if (props != null) {
      Map<String, Object> headers = props.getHeaders();
      if (headers != null) {
        Object oMessageType = headers.get(MessageHeaders.MESSAGE_TYPE_NAME);
        if (oMessageType != null) {
          messageType = oMessageType.toString();
        }
      }
    }
    return messageType;
  }
}
