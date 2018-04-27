package utils.http;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface RequestBuilderFunction {

  public void accept(RequestBuilder builder) throws JsonProcessingException;


}
