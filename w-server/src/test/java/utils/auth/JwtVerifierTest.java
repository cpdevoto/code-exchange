package utils.auth;

import static org.mockito.ArgumentMatchers.eq;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ninja.utils.NinjaProperties;
import utils.auth.keycloak.KeycloakKeyProvider;

public class JwtVerifierTest {

  private KeyProvider keyProvider;
  private JwtFactory jwtFactory;

  @Before
  public void setup() {
    NinjaProperties config = mock(NinjaProperties.class);
    ObjectMapper jsonMapper = new ObjectMapper();

    when(config.get(eq("keycloak.issuer")))
        .thenReturn("https://id.dev.apiv2.wantify.com/auth/realms/wantify");
    when(config.get(eq("keycloak.keys.default")))
        .thenReturn(
            "{\"keys\":[{"
                + "\"kid\":\"Hd7YXXMRZu38A1EPVAeTUkDLEkGwa8S3toZb9Bdz42Y\","
                + "\"kty\":\"RSA\","
                + "\"alg\":\"RS256\","
                + "\"use\":\"sig\","
                + "\"n\":\"uEJ1l9fIPGCS-7bqdx1IM5hk_DoruF-_i-AUYPQo-AlEwIfRXW0oOFD4_ohwE1_PuO7LBJxjlOrurSVN5TcXBmoLvg7WaUlw7e9TXNy717Iu57DKcRYRddEYdQoYTiLF_q5p2DCXmtMxtF7_h4D9OiAicUUapAn8J6s1E4UAtJeO2UMBQxAIyJumS5Av35ewLa60nhNFvBuIFb50LBdFrIKytQ52loAxtjwsdq8WBzOSg3xEtiRbooDtUq1viYdQ5G0hYR4--xU8XIlLQHDsdb0_Psk6FWh5g6Csp7-IhOJKVcvZXPADS4fgi_v22tPj2aIODrtVaqMUBHvxlfcBjw\","
                + "\"e\":\"AQAB\""
                + "}]}");

    this.keyProvider = new KeycloakKeyProvider(config, jsonMapper);
    this.jwtFactory = new JwtFactory(jsonMapper);

  }


  @Test
  public void test_verify() throws JwtException {
    JwtVerifier verifier = new JwtVerifier(keyProvider);

    Jwt jwt = jwtFactory.newJwt()
        .parseAuthHeader(AuthHeaders.TEST_HEADER);

    verifier.verify(jwt);
  }

}
