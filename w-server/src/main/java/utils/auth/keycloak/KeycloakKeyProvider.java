package utils.auth.keycloak;

import static java.util.Objects.requireNonNull;
import static utils.auth.keycloak.KeycloakProps.DEFAULT_KEYS;
import static utils.auth.keycloak.KeycloakProps.ISSUER;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.utils.NinjaProperties;
import utils.auth.Algorithm;
import utils.auth.KeyProvider;
import utils.auth.base64.Base64Url;
import utils.http.SimpleHttpRequestFactory;

@Singleton
public class KeycloakKeyProvider implements KeyProvider {
  private static final Logger log = LoggerFactory.getLogger(KeycloakKeyProvider.class);
  private static final Set<Algorithm> SUPPORTED_ALGORITHMS = ImmutableSet.of(Algorithm.RS256);
  private static final String KEYCLOAK_CERTS_PATH = "/protocol/openid-connect/certs";

  private final SimpleHttpRequestFactory requestFactory;
  private final Optional<Map<Algorithm, PublicKey>> defaultKeys;
  private final ObjectMapper jsonMapper;

  private final Supplier<Map<Algorithm, PublicKey>> keys = Suppliers.memoizeWithExpiration(
      () -> fetchKeys(), 1, TimeUnit.HOURS);

  @Inject
  public KeycloakKeyProvider(NinjaProperties config, ObjectMapper jsonMapper) {
    requireNonNull(config, "config cannot be null");
    requireNonNull(jsonMapper, "jsonMapper cannot be null");
    String issuer = requireNonNull(config.get(ISSUER),
        "Expected a configuration property named " + ISSUER);
    this.requestFactory = SimpleHttpRequestFactory.builder(issuer)
        .withConnectTimeout(30, TimeUnit.SECONDS)
        .withReadTimeout(30, TimeUnit.SECONDS)
        .build();
    this.jsonMapper = jsonMapper;
    this.defaultKeys = parseDefaultKeys(config.get(DEFAULT_KEYS));
  }

  @Override
  public PublicKey getPublicKey(Algorithm algorithm) {
    return keys.get().get(algorithm);
  }

  private Map<Algorithm, PublicKey> fetchKeys() {
    Map<Algorithm, PublicKey> keys = null;
    try {
      KeycloakPublicKeys keycloakKeys = requestFactory.newRequest()
          .withUrl(KEYCLOAK_CERTS_PATH)
          .execute(KeycloakPublicKeys.class);
      keys = toPublicKeys(keycloakKeys);
    } catch (Exception e) {
      log.error(
          "A problem occurred while attempting to fetch the keycloak keys. Using the default keys",
          e);
      if (defaultKeys.isPresent()) {
        keys = defaultKeys.get();
      }
    }
    if (keys == null) {
      throw new RuntimeException("Unable to initialize the JWT authentication module with keys");
    }
    return keys;
  }

  private Optional<Map<Algorithm, PublicKey>> parseDefaultKeys(String json) {
    if (json == null) {
      return Optional.empty();
    }
    try {
      KeycloakPublicKeys keycloakKeys = jsonMapper.readValue(json, KeycloakPublicKeys.class);
      Map<Algorithm, PublicKey> keys = toPublicKeys(keycloakKeys);
      if (keys.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(keys);
    } catch (Exception e) {
      log.error("A problem occurred while attempting to parse the default Keycloak keys", e);
      return Optional.empty();
    }
  }

  private Map<Algorithm, PublicKey> toPublicKeys(KeycloakPublicKeys keycloakKeys) throws Exception {
    if (keycloakKeys.getKeys().isEmpty()) {
      log.error(
          "A problem occurred while attempting to parse the default Keycloak keys: expected at least one key");
    }
    Map<Algorithm, PublicKey> keys = Maps.newHashMap();
    for (KeycloakPublicKey keycloakKey : keycloakKeys.getKeys()) {
      Optional<PublicKey> key = toPublicKey(keycloakKey);
      if (key.isPresent()) {
        keys.put(toAlgorithm(keycloakKey), key.get());
      }
    }
    return keys;
  }

  private Optional<PublicKey> toPublicKey(KeycloakPublicKey keycloakKey) throws Exception {
    Algorithm algorithm = toAlgorithm(keycloakKey);
    if (algorithm == null || !SUPPORTED_ALGORITHMS.contains(algorithm)) {
      log.warn("Unsupported public key algorithm: " + keycloakKey.getAlgorithm());
      return Optional.empty();
    }
    BigInteger modulus = new BigInteger(1, Base64Url.decode(keycloakKey.getModulus()));
    BigInteger publicExponent =
        new BigInteger(1, Base64Url.decode(keycloakKey.getExponent()));
    PublicKey publicKey = KeyFactory.getInstance("RSA")
        .generatePublic(new RSAPublicKeySpec(modulus, publicExponent));
    return Optional.of(publicKey);
  }

  private Algorithm toAlgorithm(KeycloakPublicKey keycloakKey) {
    return Algorithm.get(keycloakKey.getAlgorithm());
  }

}
