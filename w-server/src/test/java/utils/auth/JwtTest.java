package utils.auth;

import static utils.auth.AuthHeaders.TEST_HEADER;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class JwtTest {

  private JwtFactory jwtFactory = new JwtFactory(new ObjectMapper());


  @Test
  public void test_parse() throws JwtException {

    Jwt jwt = jwtFactory.newJwt()
        .parseAuthHeader(TEST_HEADER);

    assertThat(jwt, notNullValue());
    assertThat(jwt.getContentBytes(), notNullValue());
    assertThat(jwt.getSignatureBytes(), notNullValue());
    assertThat(jwt.getHeader(), notNullValue());
    assertThat(jwt.getPayload(), notNullValue());

    assertThat(jwt.getHeader().getKeyId(), equalTo("Hd7YXXMRZu38A1EPVAeTUkDLEkGwa8S3toZb9Bdz42Y"));
    assertThat(jwt.getHeader().getType(), equalTo("JWT"));
    assertThat(jwt.getHeader().getAlgorithm(), equalTo(Algorithm.RS256));

    assertThat(jwt.getPayload().getTokenId(), equalTo("6b1f72c0-54dc-4058-90d1-55a4da828dbe"));
    assertThat(jwt.getPayload().getExpiration(), equalTo(1523318266000L));
    assertThat(jwt.getPayload().getNotBefore(), equalTo(0L));
    assertThat(jwt.getPayload().getIssuedAt(), equalTo(1523317966000L));
    assertThat(jwt.getPayload().getIssuer(),
        equalTo("https://id.dev.apiv2.wantify.com/auth/realms/wantify"));
    assertThat(jwt.getPayload().getAudience(), equalTo("wantify-server-local"));
    assertThat(jwt.getPayload().getSubject(), equalTo("c5440f24-b740-4d70-addf-cf9141fb8223"));
    assertThat(jwt.getPayload().getName(), equalTo("Carlos Devoto"));
    assertThat(jwt.getPayload().getPreferredUserName(), equalTo("cdevoto"));
    assertThat(jwt.getPayload().getGivenName(), equalTo("Carlos"));
    assertThat(jwt.getPayload().getFamilyName(), equalTo("Devoto"));
    assertThat(jwt.getPayload().getEmail(), equalTo("cpdevoto@gmail.com"));
  }

  @Test
  public void test_parse_assert_expiration() throws JwtException {

    // Time is at expiration
    jwtFactory.newJwt()
        .assertExpiration(1523318266000L)
        .parseAuthHeader(TEST_HEADER);

    // Time is before expiration
    jwtFactory.newJwt()
        .assertExpiration(1523318265000L)
        .parseAuthHeader(TEST_HEADER);

    try {
      // Time is after expiration
      jwtFactory.newJwt()
          .assertExpiration(1523318267000L)
          .parseAuthHeader(TEST_HEADER);
      fail("Expected a JwtAssertionException");
    } catch (JwtAssertionException e) {
    }

  }

  @Test
  public void test_parse_assert_not_before() throws JwtException {

    // Time is at not before
    jwtFactory.newJwt()
        .assertNotBefore(0L)
        .parseAuthHeader(TEST_HEADER);

    // Time is after not before
    jwtFactory.newJwt()
        .assertNotBefore(1L)
        .parseAuthHeader(TEST_HEADER);

    try {
      // Time is before not before
      jwtFactory.newJwt()
          .assertNotBefore(-1L)
          .parseAuthHeader(TEST_HEADER);
      fail("Expected a JwtAssertionException");
    } catch (JwtAssertionException e) {
    }

  }

  @Test
  public void test_parse_assert_issuer() throws JwtException {

    // Issuer is same
    jwtFactory.newJwt()
        .assertIssuer("https://id.dev.apiv2.wantify.com/auth/realms/wantify")
        .parseAuthHeader(TEST_HEADER);

    try {
      // Issuer is different
      jwtFactory.newJwt()
          .assertIssuer("https://id.dev.apiv2.wantify.com/auth/realms/master")
          .parseAuthHeader(TEST_HEADER);
      fail("Expected a JwtAssertionException");
    } catch (JwtAssertionException e) {
    }

  }

  @Test
  public void test_parse_assert_audience() throws JwtException {

    // Audience is same
    jwtFactory.newJwt()
        .assertAudience("wantify-server-local")
        .parseAuthHeader(TEST_HEADER);

    try {
      // Audience is different
      jwtFactory.newJwt()
          .assertAudience("wantify-server")
          .parseAuthHeader(TEST_HEADER);
      fail("Expected a JwtAssertionException");
    } catch (JwtAssertionException e) {
    }

  }

  @Test
  public void test_parse_assert_algorithm() throws JwtException {

    // Algorithm is same
    jwtFactory.newJwt()
        .assertAlgorithm(Algorithm.RS256)
        .parseAuthHeader(TEST_HEADER);

    // There are currently no other algorithms to test with
    // so we cannot test the case where the algorithms are different

  }
}
