package utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.lifecycle.Start;

@Singleton
public class WantifyObjectMapper {

  @Inject
  ObjectMapper objectMapper;

  @Start(order = 90)
  public void configureObjectMapper() {
    objectMapper.registerModule(new Jdk8Module());
  }
}
