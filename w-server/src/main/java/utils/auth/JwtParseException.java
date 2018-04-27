package utils.auth;

public class JwtParseException extends JwtException {

  private static final long serialVersionUID = 1L;

  public JwtParseException() {
    super();
  }

  public JwtParseException(String message, Throwable cause) {
    super(message, cause);
  }

  public JwtParseException(String message) {
    super(message);
  }

  public JwtParseException(Throwable cause) {
    super(cause);
  }


}
