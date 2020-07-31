package utils.auth.keycloak;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableList;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = KeycloakPublicKeys.Builder.class)
public class KeycloakPublicKeys {
  private final List<KeycloakPublicKey> keys;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(KeycloakPublicKeys publicKeys) {
    return new Builder(publicKeys);
  }

  private KeycloakPublicKeys(Builder builder) {
    this.keys = builder.keys;
  }

  public List<KeycloakPublicKey> getKeys() {
    return keys;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private List<KeycloakPublicKey> keys;

    private Builder() {}

    private Builder(KeycloakPublicKeys publicKeys) {
      requireNonNull(publicKeys, "publicKeys cannot be null");
      this.keys = publicKeys.keys;
    }

    public Builder withKeys(List<KeycloakPublicKey> keys) {
      requireNonNull(keys, "keys cannot be null");
      this.keys = ImmutableList.copyOf(keys);
      return this;
    }

    public KeycloakPublicKeys build() {
      requireNonNull(keys, "keys cannot be null");
      return new KeycloakPublicKeys(this);
    }
  }
}
