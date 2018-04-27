package utils.auth;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static utils.auth.Claims.ALG;
import static utils.auth.Claims.AUD;
import static utils.auth.Claims.EMAIL;
import static utils.auth.Claims.EXP;
import static utils.auth.Claims.FAMILY_NAME;
import static utils.auth.Claims.GIVEN_NAME;
import static utils.auth.Claims.IAT;
import static utils.auth.Claims.ISS;
import static utils.auth.Claims.JTI;
import static utils.auth.Claims.KID;
import static utils.auth.Claims.NAME;
import static utils.auth.Claims.NBF;
import static utils.auth.Claims.PREFERRED_USERNAME;
import static utils.auth.Claims.SUB;
import static utils.auth.Claims.TYP;
import static utils.auth.Jwt.BEARER_PREFIX;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import utils.auth.base64.Base64Url;

public class JwtFactory {
  private static final String HEADER_ERROR =
      "Expected a header property named %s with a(n) %s value";
  private static final String PAYLOAD_ERROR =
      "Expected a payload property named %s with a(n) %s value";

  private final ObjectMapper jsonMapper;

  @Inject
  public JwtFactory(ObjectMapper jsonMapper) {
    this.jsonMapper = requireNonNull(jsonMapper, "jsonMapper cannot be null");
  }

  public Parser newJwt() {
    return new Parser();
  }

  public class Parser {
    private final List<JwtAssertion> assertions = Lists.newArrayList();

    private Parser() {}

    public Parser assertExpiration(final long time) {
      assertions.add(new JwtAssertion(
          (jwt) -> jwt.getPayload().getExpiration() >= time,
          (jwt) -> "The token has expired"));
      return this;
    }

    public Parser assertNotBefore(final long time) {
      assertions.add(new JwtAssertion(
          (jwt) -> jwt.getPayload().getNotBefore() <= time,
          (jwt) -> "The token cannot be used yet"));
      return this;
    }

    public Parser assertIssuer(final String issuer) {
      requireNonNull(issuer, "issuer cannot be null");
      assertions.add(new JwtAssertion(
          (jwt) -> issuer.equals(jwt.getPayload().getIssuer()),
          (jwt) -> "Unexpected token issuer " + jwt.getPayload().getIssuer()));
      return this;
    }

    public Parser assertAudience(final String audience) {
      requireNonNull(audience, "audience cannot be null");
      assertions.add(new JwtAssertion(
          (jwt) -> audience.equals(jwt.getPayload().getAudience()),
          (jwt) -> "Unexpected token audience " + jwt.getPayload().getIssuer()));
      return this;
    }

    public Parser assertAlgorithm(final Algorithm algorithm) {
      requireNonNull(algorithm, "algorithm cannot be null");
      assertions.add(new JwtAssertion(
          (jwt) -> jwt.getHeader().getAlgorithm() == algorithm,
          (jwt) -> "Unexpected token algorithm " + jwt.getHeader().getAlgorithm()));
      return this;

    }

    public Jwt parseAuthHeader(String authHeader) throws JwtException {
      requireNonNull(authHeader, "authHeader cannot be null");
      authHeader = authHeader.trim();
      checkArgument(authHeader.startsWith(BEARER_PREFIX),
          "Expected authHeader to start with '" + BEARER_PREFIX + "'");
      String encodedToken = authHeader.substring(BEARER_PREFIX.length());
      return parse(encodedToken);
    }

    public Jwt parse(String encodedToken) throws JwtException {
      int firstPeriod = encodedToken.indexOf('.');
      int lastPeriod = encodedToken.lastIndexOf('.');
      if (firstPeriod == -1 || firstPeriod == 0 || firstPeriod + 1 >= lastPeriod
          || lastPeriod == encodedToken.length() - 1) {
        throw new JwtParseException(
            "Invalid JWT format: Expected an encoded header followed by a period "
                + "followed by an encoded payload followed by another period "
                + "followed by an encoded signature");
      }

      String encodedHeader = encodedToken.substring(0, firstPeriod);
      String encodedPayload = encodedToken.substring(firstPeriod + 1, lastPeriod);
      String encodedSignature = encodedToken.substring(lastPeriod + 1);

      String header = new String(Base64Url.decode(encodedHeader), Charsets.UTF_8);
      String payload = new String(Base64Url.decode(encodedPayload), Charsets.UTF_8);

      byte[] contentBytes = encodedToken.substring(0, lastPeriod).getBytes(Charsets.UTF_8);
      byte[] signatureBytes = Base64Url.decode(encodedSignature);

      Jwt jwt = toJwt(header, payload, contentBytes, signatureBytes);
      for (JwtAssertion assertion : assertions) {
        assertion.assertJwt(jwt);
      }
      return jwt;
    }

    private Jwt toJwt(String header, String payload, byte[] contentBytes, byte[] signatureBytes)
        throws JwtParseException {
      try {
        JsonNode headerNode = jsonMapper.readTree(header);
        String algString = expectString(headerNode, ALG, HEADER_ERROR);
        Algorithm algorithm = Algorithm.get(algString);
        String type = expectString(headerNode, TYP, HEADER_ERROR);
        String keyId = expectString(headerNode, KID, HEADER_ERROR);

        JwtHeader jwtHeader = JwtHeader.builder()
            .withKeyId(keyId)
            .withType(type)
            .withAlgorithm(algorithm)
            .build();

        JsonNode payloadNode = jsonMapper.readTree(payload);
        String issuer = expectString(payloadNode, ISS, PAYLOAD_ERROR);
        String subject = expectString(payloadNode, SUB, PAYLOAD_ERROR);
        String audience = expectString(payloadNode, AUD, PAYLOAD_ERROR);
        String tokenId = expectString(payloadNode, JTI, PAYLOAD_ERROR);
        long expiration = expectLong(payloadNode, EXP, PAYLOAD_ERROR) * 1000;
        long notBefore = expectLong(payloadNode, NBF, PAYLOAD_ERROR) * 1000;
        long issuedAt = expectLong(payloadNode, IAT, PAYLOAD_ERROR) * 1000;
        String name = expectString(payloadNode, NAME, PAYLOAD_ERROR);
        String preferredUsername = expectString(payloadNode, PREFERRED_USERNAME, PAYLOAD_ERROR);
        String givenName = expectString(payloadNode, GIVEN_NAME, PAYLOAD_ERROR);
        String familyName = expectString(payloadNode, FAMILY_NAME, PAYLOAD_ERROR);
        String email = expectString(payloadNode, EMAIL, PAYLOAD_ERROR);

        JwtPayload jwtPayload = JwtPayload.builder()
            .withIssuer(issuer)
            .withSubject(subject)
            .withAudience(audience)
            .withTokenId(tokenId)
            .withExpiration(expiration)
            .withNotBefore(notBefore)
            .withIssuedAt(issuedAt)
            .withName(name)
            .withPreferredUserName(preferredUsername)
            .withGivenName(givenName)
            .withFamilyName(familyName)
            .withEmail(email)
            .build();

        return Jwt.builder()
            .withHeader(jwtHeader)
            .withPayload(jwtPayload)
            .withContentBytes(contentBytes)
            .withSignatureBytes(signatureBytes)
            .build();
      } catch (Exception e) {
        throw new JwtParseException("A problem occurred while attempting to parse a JWT token", e);
      }
    }

    private String expectString(JsonNode node, String name, String errorTemplate)
        throws JwtParseException {
      JsonNode prop = node.get(name);
      if (prop == null || !prop.isTextual()) {
        throw new JwtParseException(String.format(errorTemplate, name, "string"));
      }
      return prop.asText();
    }

    private long expectLong(JsonNode node, String name, String errorTemplate)
        throws JwtParseException {
      JsonNode prop = node.get(name);
      if (prop == null || !prop.isIntegralNumber()) {
        throw new JwtParseException(String.format(errorTemplate, name, "integer"));
      }
      return prop.asLong();
    }
  }
}
