package utils.auth;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import utils.auth.keycloak.KeycloakKeyProvider;

public class JwtAuthModule extends AbstractModule {

  @Override
  protected void configure() {

    bind(JwtFactory.class).in(Singleton.class);
    bind(KeyProvider.class).to(KeycloakKeyProvider.class).in(Singleton.class);

  }


}
