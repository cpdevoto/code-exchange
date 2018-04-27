package utils.auth;

public class JwtVerificationException extends JwtException {

  private static final long serialVersionUID = 1L;

  public JwtVerificationException() {}

  public JwtVerificationException(String message) {
    super(message);
  }

  public JwtVerificationException(Throwable cause) {
    super(cause);
  }

  public JwtVerificationException(String message, Throwable cause) {
    super(message, cause);
  }

  public JwtVerificationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
