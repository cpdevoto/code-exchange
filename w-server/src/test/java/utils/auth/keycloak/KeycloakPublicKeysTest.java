package utils.auth.keycloak;

import java.io.IOException;

import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableList;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import testutils.rules.jsonvalidator.JsonValidator;
import utils.auth.keycloak.KeycloakPublicKey;
import utils.auth.keycloak.KeycloakPublicKeys;

public class KeycloakPublicKeysTest {
  @ClassRule
  public static JsonValidator validator = JsonValidator.builder()
      .withJsonResource(KeycloakPublicKeysTest.class, "public-key.json")
      .build();

  @Test
  public void test_json_deserialization() throws JsonParseException, JsonMappingException, IOException {
    KeycloakPublicKeys keys = validator.deserialize(KeycloakPublicKeys.class);
    assertThat(keys,  notNullValue());
    assertThat(keys.getKeys(),  notNullValue());
    assertThat(keys.getKeys().size(), equalTo(1));
    assertThat(keys.getKeys().get(0).getId(), equalTo("Hd7YXXMRZu38A1EPVAeTUkDLEkGwa8S3toZb9Bdz42Y"));
    assertThat(keys.getKeys().get(0).getType(), equalTo("RSA"));
    assertThat(keys.getKeys().get(0).getAlgorithm(), equalTo("RS256"));
    assertThat(keys.getKeys().get(0).getUse(), equalTo("sig"));
    assertThat(keys.getKeys().get(0).getModulus(), equalTo("uEJ1l9fIPGCS-7bqdx1IM5hk_DoruF-_i-AUYPQo-AlEwIfRXW0oOFD4_ohwE1_PuO7LBJxjlOrurSVN5TcXBmoLvg7WaUlw7e9TXNy717Iu57DKcRYRddEYdQoYTiLF_q5p2DCXmtMxtF7_h4D9OiAicUUapAn8J6s1E4UAtJeO2UMBQxAIyJumS5Av35ewLa60nhNFvBuIFb50LBdFrIKytQ52loAxtjwsdq8WBzOSg3xEtiRbooDtUq1viYdQ5G0hYR4--xU8XIlLQHDsdb0_Psk6FWh5g6Csp7-IhOJKVcvZXPADS4fgi_v22tPj2aIODrtVaqMUBHvxlfcBjw"));
    assertThat(keys.getKeys().get(0).getExponent(), equalTo("AQAB"));
  }

  @Test
  public void test_json_serialization() throws JsonParseException, JsonMappingException, IOException {
    KeycloakPublicKeys keys = KeycloakPublicKeys.builder()
        .withKeys(ImmutableList.of(
            KeycloakPublicKey.builder()
            .withId("Hd7YXXMRZu38A1EPVAeTUkDLEkGwa8S3toZb9Bdz42Y")
            .withType("RSA")
            .withAlgorithm("RS256")
            .withUse("sig")
            .withModulus("uEJ1l9fIPGCS-7bqdx1IM5hk_DoruF-_i-AUYPQo-AlEwIfRXW0oOFD4_ohwE1_PuO7LBJxjlOrurSVN5TcXBmoLvg7WaUlw7e9TXNy717Iu57DKcRYRddEYdQoYTiLF_q5p2DCXmtMxtF7_h4D9OiAicUUapAn8J6s1E4UAtJeO2UMBQxAIyJumS5Av35ewLa60nhNFvBuIFb50LBdFrIKytQ52loAxtjwsdq8WBzOSg3xEtiRbooDtUq1viYdQ5G0hYR4--xU8XIlLQHDsdb0_Psk6FWh5g6Csp7-IhOJKVcvZXPADS4fgi_v22tPj2aIODrtVaqMUBHvxlfcBjw")
            .withExponent("AQAB")
            .build()
            ))
        .build();
    
    validator.assertJson(keys);
  }
}
