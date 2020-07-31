package utils.auth;

import static java.util.Objects.requireNonNull;

public class Jwt {
  public static final String BEARER_PREFIX = "Bearer ";

  private final JwtHeader header;
  private final JwtPayload payload;
  private final byte[] contentBytes;
  private final byte[] signatureBytes;

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Jwt jwt) {
    return new Builder(jwt);
  }

  private Jwt(Builder builder) {
    this.header = builder.header;
    this.payload = builder.payload;
    this.contentBytes = builder.contentBytes;
    this.signatureBytes = builder.signatureBytes;
  }

  public JwtHeader getHeader() {
    return header;
  }

  public JwtPayload getPayload() {
    return payload;
  }

  public byte[] getContentBytes() {
    return contentBytes;
  }

  public byte[] getSignatureBytes() {
    return signatureBytes;
  }

  public AuthUser toAuthUser() {
    return AuthUser.builder()
        .withEmail(payload.getEmail())
        .withFamilyName(payload.getFamilyName())
        .withGivenName(payload.getGivenName())
        .withName(payload.getName())
        .withPreferredUserName(payload.getPreferredUserName())
        .withSubject(payload.getSubject())
        .build();
  }

  public static class Builder {
    private JwtHeader header;
    private JwtPayload payload;
    private byte[] contentBytes;
    private byte[] signatureBytes;

    private Builder() {}

    private Builder(Jwt jwt) {
      requireNonNull(jwt, "jwt cannot be null");
      this.header = jwt.header;
      this.payload = jwt.payload;
      this.contentBytes = jwt.contentBytes;
      this.signatureBytes = jwt.signatureBytes;
    }

    public Builder withHeader(JwtHeader header) {
      requireNonNull(header, "header cannot be null");
      this.header = header;
      return this;
    }

    public Builder withPayload(JwtPayload payload) {
      requireNonNull(payload, "payload cannot be null");
      this.payload = payload;
      return this;
    }

    public Builder withContentBytes(byte[] contentBytes) {
      this.contentBytes = contentBytes;
      return this;
    }

    public Builder withSignatureBytes(byte[] signatureBytes) {
      this.signatureBytes = signatureBytes;
      return this;
    }

    public Jwt build() {
      requireNonNull(header, "header cannot be null");
      requireNonNull(payload, "payload cannot be null");
      requireNonNull(contentBytes, "contentBytes cannot be null");
      requireNonNull(signatureBytes, "signatureBytes cannot be null");
      return new Jwt(this);
    }
  }
}
