package utils.auth.keycloak;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = KeycloakPublicKey.Builder.class)
public class KeycloakPublicKey {
  private final String id;
  private final String type;
  private final String algorithm;
  private final String use;
  private final String modulus;
  private final String exponent;

  @JsonCreator
  public static Builder builder () {
    return new Builder();
  }

  public static Builder builder (KeycloakPublicKey key) {
    return new Builder(key);
  }

  private KeycloakPublicKey (Builder builder) {
    this.id = builder.id;
    this.type = builder.type;
    this.algorithm = builder.algorithm;
    this.use = builder.use;
    this.modulus = builder.modulus;
    this.exponent = builder.exponent;
  }

  @JsonProperty("kid")
  public String getId() {
    return id;
  }

  @JsonProperty("kty")
  public String getType() {
    return type;
  }

  @JsonProperty("alg")
  public String getAlgorithm() {
    return algorithm;
  }

  @JsonProperty("use")
  public String getUse() {
    return use;
  }

  @JsonProperty("n")
  public String getModulus() {
    return modulus;
  }

  @JsonProperty("e")
  public String getExponent() {
    return exponent;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private String id;
    private String type;
    private String algorithm;
    private String use;
    private String modulus;
    private String exponent;

    private Builder() {}

    private Builder(KeycloakPublicKey key) {
      requireNonNull(key, "key cannot be null");
      this.id = key.id;
      this.type = key.type;
      this.algorithm = key.algorithm;
      this.use = key.use;
      this.modulus = key.modulus;
      this.exponent = key.exponent;
    }

    @JsonProperty("kid")
    public Builder withId(String id) {
      requireNonNull(id, "id cannot be null");
      this.id = id;
      return this;
    }

    @JsonProperty("kty")
    public Builder withType(String type) {
      requireNonNull(type, "type cannot be null");
      this.type = type;
      return this;
    }

    @JsonProperty("alg")
    public Builder withAlgorithm(String algorithm) {
      requireNonNull(algorithm, "algorithm cannot be null");
      this.algorithm = algorithm;
      return this;
    }

    @JsonProperty("use")
    public Builder withUse(String use) {
      requireNonNull(use, "use cannot be null");
      this.use = use;
      return this;
    }

    @JsonProperty("n")
    public Builder withModulus(String modulus) {
      requireNonNull(modulus, "modulus cannot be null");
      this.modulus = modulus;
      return this;
    }

    @JsonProperty("e")
    public Builder withExponent(String exponent) {
      requireNonNull(exponent, "exponent cannot be null");
      this.exponent = exponent;
      return this;
    }

    public KeycloakPublicKey build() {
      requireNonNull(id, "id cannot be null");
      requireNonNull(type, "type cannot be null");
      requireNonNull(algorithm, "algorithm cannot be null");
      requireNonNull(use, "use cannot be null");
      requireNonNull(modulus, "modulus cannot be null");
      requireNonNull(exponent, "exponent cannot be null");
      return new KeycloakPublicKey(this);
    }
  }
}