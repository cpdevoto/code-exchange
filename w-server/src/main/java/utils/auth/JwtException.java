package utils.auth;

public class JwtException extends Exception {

  private static final long serialVersionUID = 1L;

  public JwtException() {}

  public JwtException(String message) {
    super(message);
  }

  public JwtException(Throwable cause) {
    super(cause);
  }

  public JwtException(String message, Throwable cause) {
    super(message, cause);
  }

  public JwtException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
