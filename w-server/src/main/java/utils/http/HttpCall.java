package utils.http;

import java.io.IOException;

@FunctionalInterface
public interface HttpCall {
  public void execute() throws IOException;
}
