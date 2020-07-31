package com.resolute.okhttp3.simple;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resolute.okhttp3.simple.utils.ResponseUtils;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class SimpletHttpRequestTest {

  private static final ObjectMapper mapper = HttpUtilsHelper.MAPPER;
  private static final String serializedCert =
      "MIIDtzCCAp+gAwIBAgIEO+cY2zANBgkqhkiG9w0BAQsFADCBizELMAkGA1UEBhMCVVMxETAPBgNVBAgTCE1pY2hpZ2FuMRMwEQYDVQQHEwpCaXJtaW5naGFtMScwJQYDVQQKEx5SZXNvbHV0ZSBCdWlsZGluZyBJbnRlbGxpZ2VuY2UxEzARBgNVBAsTClRlY2hub2xvZ3kxFjAUBgNVBAMTDUNhcmxvcyBEZXZvdG8wHhcNMTkwNjE3MTQ0OTEwWhcNNDYxMTAyMTQ0OTEwWjCBizELMAkGA1UEBhMCVVMxETAPBgNVBAgTCE1pY2hpZ2FuMRMwEQYDVQQHEwpCaXJtaW5naGFtMScwJQYDVQQKEx5SZXNvbHV0ZSBCdWlsZGluZyBJbnRlbGxpZ2VuY2UxEzARBgNVBAsTClRlY2hub2xvZ3kxFjAUBgNVBAMTDUNhcmxvcyBEZXZvdG8wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCHVD2Dbo1xyOFMyACwJy6HofI6+u0dCGqxbzpsfKm/ejnmeB1BUjCFPTrjhtAREz2L4rbneBL3BfNLKbqdlialAQPe/P+FyLso2abVYCiR/sSolQvuG7rpNTzl/HrErrpfIgOfqdkiAssg85Iq4UiErwt5ql5VeLaukpsmiVOxeqDjHpHpVkyMTOq+AFXEMYhI7lni32gTM59sE3N8U70LXNPtDtYGWyPxqeQRDZlAybwH/zbHXfXTUAJx85VmcujflzUo2VoGjlrkPbDSGixJiinP7V5f8LRaM+MFpuvDTGVtFJPmsI2SQUNghFJaDsBgvOFCxjD8tZmwELlOtzjLAgMBAAGjITAfMB0GA1UdDgQWBBS8v00M05gOoraPGAKn+49s9g1sjzANBgkqhkiG9w0BAQsFAAOCAQEADIwHvpAfy9p2jMyiRtmzRhUVGJF5u7bsdTVZJRSG2IgBKZU1ktFwaHh6sZHfT/shEAU8TbrC9jJKnVIoLpnYFaUPVQEwAnjrYC465Xcr/Qkwkm8z370Qwv2to0/GyIK0wJES/IvhodJMttPb8jDYHlhcgefk/I3SSuifhhnFYgEIeHbuuEPGgD2npQ6Mp3yOl2vIuxxy1OeQbwJs/fxy4OlsKcMWzoata7UMcCuikcEHMjga1S1Qu3Hywj1FBLdmOQM8KOSWZsYv4xqzB8EgXAWBS4S7lG9YwmdB/tPjpm32X4OvZSeaNscYaEftqkp5Xm0fkIjuZFr9+8FffElKcw==";

  private static MockWebServer httpServer;
  private static MockWebServer httpsServer;
  private static SimpleHttpRequestFactory httpRequestFactory;
  private static SimpleHttpRequestFactory httpsRequestFactory;


  @BeforeClass
  public static void setup() throws IOException {
    httpServer = new MockWebServer();
    httpsServer = new MockWebServer();
    configureServerSsl(httpsServer);

    final Dispatcher dispatcher = new Dispatcher() {

      @Override
      public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

        try {
          if (request.getPath().equals("/user/invite") && request.getMethod().equals("GET")) {
            String responseBody = mapper.writeValueAsString(
                new InviteStatus("b558664d-fcf7-48e8-9807-e7a7614f22bc", "new"));
            return new MockResponse().setResponseCode(200).setBody(responseBody);

          } else if (request.getPath().equals("/users") && request.getMethod().equals("POST")) {
            String auth = request.getHeader("Authorization");
            if (auth != null && "Bearer abcdefg0xy".equals(auth)) {
              String requestBody = request.getBody().readUtf8();
              User user = mapper.readValue(requestBody, User.class);
              if (!"cdevoto".equals(user.getUserId()) || !"password".equals(user.getPassword())) {
                return new MockResponse().setResponseCode(400);
              }
              return new MockResponse().setResponseCode(200);
            } else {
              String responseBody = "You are not authorized!";
              return new MockResponse().setResponseCode(401).setBody(responseBody);
            }

          } else {
            return new MockResponse().setResponseCode(404);
          }
        } catch (Exception e) {
          return new MockResponse().setResponseCode(500);
        }
      }
    };

    httpServer.setDispatcher(dispatcher);
    httpsServer.setDispatcher(dispatcher);

    httpRequestFactory =
        SimpleHttpRequestFactory.builder("http://localhost:" + httpServer.getPort())
            .withConnectTimeout(30, TimeUnit.SECONDS)
            .withReadTimeout(30, TimeUnit.SECONDS)
            .build();

    httpsRequestFactory =
        SimpleHttpRequestFactory.builder("https://localhost:" + httpsServer.getPort())
            .withConnectTimeout(30, TimeUnit.SECONDS)
            .withReadTimeout(30, TimeUnit.SECONDS)
            .withSslSocketFactory(SslSocketFactories.trustAllCertsFactory(),
                TrustManagers.trustAllCerts())
            .withHostnameVerifier(HostnameVerifiers.trustAllHostnames())
            .build();


  }

  private static void configureServerSsl(MockWebServer server) {
    try (InputStream in =
        SimpletHttpRequestTest.class.getClassLoader().getResourceAsStream("mystore.jks")) {

      char[] serverKeyStorePassword = "changeit".toCharArray();
      KeyStore serverKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      serverKeyStore.load(in, serverKeyStorePassword);

      String kmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
      KeyManagerFactory kmf = KeyManagerFactory.getInstance(kmfAlgorithm);
      kmf.init(serverKeyStore, serverKeyStorePassword);

      TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(kmfAlgorithm);
      trustManagerFactory.init(serverKeyStore);

      SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
      sslContext.init(kmf.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
      SSLSocketFactory sf = sslContext.getSocketFactory();
      server.useHttps(sf, false);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @AfterClass
  public static void teardown() throws IOException {
    // httpServer.shutdown();
    httpsServer.shutdown();
  }

  @Test
  public void test_http_get() throws IOException {

    // Test happy path
    InviteStatus status = httpRequestFactory.newRequest()
        .withUrl("/user/invite")
        .execute(InviteStatus.class);

    assertThat(status, notNullValue());
    assertThat(status.getUserUuid(), equalTo("b558664d-fcf7-48e8-9807-e7a7614f22bc"));
    assertThat(status.getStatus(), equalTo("new"));

    // Test incorrect Url
    ResponseUtils.assertErrorStatus(404, () -> {
      httpRequestFactory.newRequest()
          .withUrl("/user/invitexx")
          .execute(InviteStatus.class);
    });

  }

  @Test
  public void test_https_get() throws IOException, CertificateValidationException {

    X509Certificate cert =
        X509Certificates.getCertificate("https://localhost:" + httpsServer.getPort());

    assertThat(X509Certificates.serialize(cert), equalTo(serializedCert));
    assertThat(X509Certificates.deserialize(serializedCert), equalTo(cert));

    X509TrustManager trustManager = TrustManagers.trustCert(cert);
    SSLSocketFactory socketFactory = SslSocketFactories.trustCertFactory(trustManager);

    SimpleHttpRequestFactory httpsRequestFactory =
        SimpleHttpRequestFactory.builder("https://localhost:" + httpsServer.getPort())
            .withConnectTimeout(30, TimeUnit.SECONDS)
            .withReadTimeout(30, TimeUnit.SECONDS)
            .withSslSocketFactory(socketFactory,
                trustManager)
            .withHostnameVerifier(HostnameVerifiers.trustAllHostnames())
            .build();

    // Test happy path
    InviteStatus status = httpsRequestFactory.newRequest()
        .withUrl("/user/invite")
        .execute(InviteStatus.class);

    assertThat(status, notNullValue());
    assertThat(status.getUserUuid(), equalTo("b558664d-fcf7-48e8-9807-e7a7614f22bc"));
    assertThat(status.getStatus(), equalTo("new"));

    // Test incorrect Url
    ResponseUtils.assertErrorStatus(404, () -> {
      httpsRequestFactory.newRequest()
          .withUrl("/user/invitexx")
          .execute(InviteStatus.class);
    });

  }

  @Test
  public void test_http_post() throws IOException {

    // Test happy path
    httpRequestFactory.newRequest()
        .withUrl("/users")
        .addHeader("Authorization", "Bearer abcdefg0xy")
        .post(new User("cdevoto", "password"))
        .execute();


    // Test invalid auth
    ResponseUtils.assertErrorStatus(401, () -> {
      httpRequestFactory.newRequest()
          .withUrl("/users")
          .addHeader("Authorization", "Bearer abcdefg0xyxxxx")
          .post(new User("cdevoto", "password"))
          .execute();
    }, response -> {
      assertThat(response.body(), equalTo("You are not authorized!"));
    });

    // Same test but without ResponseUtils (included for educational purposes)
    try {
      httpRequestFactory.newRequest()
          .withUrl("/users")
          .addHeader("Authorization", "Bearer abcdefg0xyxxxx")
          .post(new User("cdevoto", "password"))
          .execute();
      fail("Expected a BadResponseException");
    } catch (BadResponseException e) {
      assertThat(e.getResponse().code(), equalTo(401));
      assertThat(e.getResponse().body(), equalTo("You are not authorized!"));
    }


    // Test bad request
    ResponseUtils.assertErrorStatus(400, () -> {
      httpRequestFactory.newRequest()
          .withUrl("/users")
          .addHeader("Authorization", "Bearer abcdefg0xy")
          .post(new User("cdevoto", "passwordxxx"))
          .execute();
    });


  }

  private static class InviteStatus {
    private String userUuid;
    private String status;

    @SuppressWarnings("unused")
    public InviteStatus() {}

    public InviteStatus(String userUuid, String status) {
      super();
      this.userUuid = userUuid;
      this.status = status;
    }

    public String getUserUuid() {
      return userUuid;
    }

    public String getStatus() {
      return status;
    }
  }

  private static class User {
    private String userId;
    private String password;

    @SuppressWarnings("unused")
    public User() {}

    public User(String userId, String password) {
      super();
      this.userId = userId;
      this.password = password;
    }

    public String getUserId() {
      return userId;
    }

    public String getPassword() {
      return password;
    }
  }

}
