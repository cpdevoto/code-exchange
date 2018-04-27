package utils.auth;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

public class JwtAssertion {
  public static final JwtAssertion EXPIRATION = new JwtAssertion(
      (jwt) -> jwt.getPayload().getExpiration() <= System.currentTimeMillis(),
      (jwt) -> "The token has expired");
  public static final JwtAssertion NOT_BEFORE = new JwtAssertion(
      (jwt) -> jwt.getPayload().getNotBefore() >= System.currentTimeMillis(),
      (jwt) -> "The token cannot be used yet");

  private final Function<Jwt, Boolean> assertion;
  private final Function<Jwt, String> errorHandler;

  public JwtAssertion(Function<Jwt, Boolean> assertion, Function<Jwt, String> errorHandler) {
    this.assertion = requireNonNull(assertion, "assertion cannot be null");
    this.errorHandler = requireNonNull(errorHandler, "errorHandler cannot be null");
  }

  public void assertJwt(Jwt jwt) throws JwtAssertionException {
    if (!assertion.apply(jwt)) {
      throw new JwtAssertionException(errorHandler.apply(jwt));
    }
  }
}
