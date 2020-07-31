package utils.auth;

import org.junit.Test;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;

import models.User;

public class AuthUserTest {

  @Test
  public void test_equals_user() {
    User user = new User();
    user.setId(1L);
    user.setSubject("e5440f24-b740-4d70-addf-cf9141fb8223");
    user.setEmail("cpdevoto@gmail.com");
    user.setPreferredUsername("cdevoto");
    user.setName("Carlos Devoto");
    user.setGivenName("Carlos");
    user.setFamilyName("Devoto");

    AuthUser authUser = AuthUser.builder()
        .withSubject("e5440f24-b740-4d70-addf-cf9141fb8223")
        .withEmail("cpdevoto@gmail.com")
        .withPreferredUserName("cdevoto")
        .withName("Carlos Devoto")
        .withGivenName("Carlos")
        .withFamilyName("Devoto")
        .build();

    assertThat(authUser.equalsUser(user), equalTo(true));
    assertThat(authUser.equalsUser(new User()), equalTo(false));
  }

}
