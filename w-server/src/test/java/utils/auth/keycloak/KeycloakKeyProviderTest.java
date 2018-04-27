package utils.auth.keycloak;

import static org.mockito.ArgumentMatchers.eq;

import java.security.PublicKey;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.notNullValue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ninja.utils.NinjaProperties;
import utils.auth.Algorithm;
import utils.auth.KeyProvider;

public class KeycloakKeyProviderTest {
  private NinjaProperties config;
  private ObjectMapper jsonMapper = new ObjectMapper();

  @Before
  public void setup() {
    config = mock(NinjaProperties.class);

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
  }

  @Test
  public void test_get_public_key() {
    KeyProvider provider = new KeycloakKeyProvider(config, jsonMapper);
    PublicKey key = provider.getPublicKey(Algorithm.RS256);
    assertThat(key, notNullValue());
  }

}
