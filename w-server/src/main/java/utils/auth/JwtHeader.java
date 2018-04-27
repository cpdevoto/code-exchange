package utils.auth;

import static java.util.Objects.requireNonNull;

public class JwtHeader {
  private final String keyId;
  private final String type;
  private final Algorithm algorithm;

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(JwtHeader jwtHeader) {
    return new Builder(jwtHeader);
  }

  private JwtHeader(Builder builder) {
    this.keyId = builder.keyId;
    this.type = builder.type;
    this.algorithm = builder.algorithm;
  }

  public String getKeyId() {
    return keyId;
  }

  public String getType() {
    return type;
  }

  public Algorithm getAlgorithm() {
    return algorithm;
  }

  public static class Builder {
    private String keyId;
    private String type;
    private Algorithm algorithm;

    private Builder() {}

    private Builder(JwtHeader jwtHeader) {
      requireNonNull(jwtHeader, "jwtHeader cannot be null");
      this.keyId = jwtHeader.keyId;
      this.type = jwtHeader.type;
      this.algorithm = jwtHeader.algorithm;
    }

    public Builder withKeyId(String keyId) {
      requireNonNull(keyId, "keyId cannot be null");
      this.keyId = keyId;
      return this;
    }

    public Builder withType(String type) {
      requireNonNull(type, "type cannot be null");
      this.type = type;
      return this;
    }

    public Builder withAlgorithm(Algorithm algorithm) {
      requireNonNull(algorithm, "algorithm cannot be null");
      this.algorithm = algorithm;
      return this;
    }

    public JwtHeader build() {
      requireNonNull(keyId, "keyId cannot be null");
      requireNonNull(type, "type cannot be null");
      requireNonNull(algorithm, "algorithm cannot be null");
      return new JwtHeader(this);
    }
  }
}
