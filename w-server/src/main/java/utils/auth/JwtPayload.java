package utils.auth;

import static java.util.Objects.requireNonNull;

public class JwtPayload {
  private final String issuer;
  private final String subject;
  private final String audience;
  private final String tokenId;
  private final long expiration;
  private final long notBefore;
  private final long issuedAt;
  private final String name;
  private final String preferredUserName;
  private final String givenName;
  private final String familyName;
  private final String email;

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(JwtPayload jwtPayload) {
    return new Builder(jwtPayload);
  }

  private JwtPayload(Builder builder) {
    this.issuer = builder.issuer;
    this.subject = builder.subject;
    this.audience = builder.audience;
    this.tokenId = builder.tokenId;
    this.expiration = builder.expiration;
    this.notBefore = builder.notBefore;
    this.issuedAt = builder.issuedAt;
    this.name = builder.name;
    this.preferredUserName = builder.preferredUserName;
    this.givenName = builder.givenName;
    this.familyName = builder.familyName;
    this.email = builder.email;
  }

  public String getIssuer() {
    return issuer;
  }

  public String getSubject() {
    return subject;
  }

  public String getAudience() {
    return audience;
  }

  public String getTokenId() {
    return tokenId;
  }

  public long getExpiration() {
    return expiration;
  }

  public long getNotBefore() {
    return notBefore;
  }

  public long getIssuedAt() {
    return issuedAt;
  }

  public String getName() {
    return name;
  }

  public String getPreferredUserName() {
    return preferredUserName;
  }

  public String getGivenName() {
    return givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public String getEmail() {
    return email;
  }

  public static class Builder {
    private String issuer;
    private String subject;
    private String audience;
    private String tokenId;
    private Long expiration;
    private Long notBefore;
    private Long issuedAt;
    private String name;
    private String preferredUserName;
    private String givenName;
    private String familyName;
    private String email;

    private Builder() {}

    private Builder(JwtPayload jwtPayload) {
      requireNonNull(jwtPayload, "jwtPayload cannot be null");
      this.issuer = jwtPayload.issuer;
      this.subject = jwtPayload.subject;
      this.audience = jwtPayload.audience;
      this.tokenId = jwtPayload.tokenId;
      this.expiration = jwtPayload.expiration;
      this.notBefore = jwtPayload.notBefore;
      this.issuedAt = jwtPayload.issuedAt;
      this.name = jwtPayload.name;
      this.preferredUserName = jwtPayload.preferredUserName;
      this.givenName = jwtPayload.givenName;
      this.familyName = jwtPayload.familyName;
      this.email = jwtPayload.email;
    }

    public Builder withIssuer(String issuer) {
      requireNonNull(issuer, "issuer cannot be null");
      this.issuer = issuer;
      return this;
    }

    public Builder withSubject(String subject) {
      requireNonNull(subject, "subject cannot be null");
      this.subject = subject;
      return this;
    }

    public Builder withAudience(String audience) {
      requireNonNull(audience, "audience cannot be null");
      this.audience = audience;
      return this;
    }

    public Builder withTokenId(String tokenId) {
      requireNonNull(tokenId, "tokenId cannot be null");
      this.tokenId = tokenId;
      return this;
    }

    public Builder withExpiration(long expiration) {
      this.expiration = expiration;
      return this;
    }

    public Builder withNotBefore(long notBefore) {
      this.notBefore = notBefore;
      return this;
    }

    public Builder withIssuedAt(long issuedAt) {
      this.issuedAt = issuedAt;
      return this;
    }

    public Builder withName(String name) {
      requireNonNull(name, "name cannot be null");
      this.name = name;
      return this;
    }

    public Builder withPreferredUserName(String preferredUserName) {
      requireNonNull(preferredUserName, "preferredUserName cannot be null");
      this.preferredUserName = preferredUserName;
      return this;
    }

    public Builder withGivenName(String givenName) {
      requireNonNull(givenName, "givenName cannot be null");
      this.givenName = givenName;
      return this;
    }

    public Builder withFamilyName(String familyName) {
      requireNonNull(familyName, "familyName cannot be null");
      this.familyName = familyName;
      return this;
    }

    public Builder withEmail(String email) {
      requireNonNull(email, "email cannot be null");
      this.email = email;
      return this;
    }

    public JwtPayload build() {
      requireNonNull(issuer, "issuer cannot be null");
      requireNonNull(subject, "subject cannot be null");
      requireNonNull(audience, "audience cannot be null");
      requireNonNull(tokenId, "tokenId cannot be null");
      requireNonNull(expiration, "expiration cannot be null");
      requireNonNull(notBefore, "notBefore cannot be null");
      requireNonNull(issuedAt, "issuedAt cannot be null");
      requireNonNull(name, "name cannot be null");
      requireNonNull(preferredUserName, "preferredUserName cannot be null");
      requireNonNull(givenName, "givenName cannot be null");
      requireNonNull(familyName, "familyName cannot be null");
      requireNonNull(email, "email cannot be null");
      return new JwtPayload(this);
    }
  }
}
