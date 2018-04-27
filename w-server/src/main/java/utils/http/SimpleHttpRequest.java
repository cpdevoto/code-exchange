package utils.http;

public class SimpleHttpRequest
    extends AbstractHttpRequest<SimpleHttpRequest> {

  SimpleHttpRequest(SimpleHttpRequestFactory factory) {
    super(factory);
  }

  @Override
  protected SimpleHttpRequest getThis() {
    return this;
  }

}
