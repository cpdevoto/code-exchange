package utils.auth;

public class JwtAssertionException extends JwtException {

  private static final long serialVersionUID = 1L;

  public JwtAssertionException() {}

  public JwtAssertionException(String message) {
    super(message);
  }

  public JwtAssertionException(Throwable cause) {
    super(cause);
  }

  public JwtAssertionException(String message, Throwable cause) {
    super(message, cause);
  }

  public JwtAssertionException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
