/**
 * Copyright (C) 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package controllers;

import static java.util.Objects.requireNonNull;
import static utils.auth.keycloak.KeycloakProps.APP_AUDIENCE;
import static utils.auth.keycloak.KeycloakProps.AUDIENCE;
import static utils.auth.keycloak.KeycloakProps.ISSUER;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.utils.NinjaProperties;
import utils.auth.AuthUser;


@Singleton
public class ApplicationController {
  private final String clientId;
  private final String appClientId;
  private final String realmUrl;

  @Inject
  public ApplicationController(NinjaProperties config) {
    requireNonNull(config, "config cannot be null");
    this.clientId = requireNonNull(config.get(AUDIENCE),
        "Expected a configuration property named " + AUDIENCE);
    this.appClientId = requireNonNull(config.get(APP_AUDIENCE),
        "Expected a configuration property named " + APP_AUDIENCE);
    this.realmUrl = requireNonNull(config.get(ISSUER),
        "Expected a configuration property named " + ISSUER);
  }

  public Result index(Context context) {
    return Results.html().render(
        views.index.template("Wantify - Index", (AuthUser) context.getAttribute("auth-user")));
  }

  public Result auth(Context context) {
    return Results.html().render(
        views.auth.template(clientId, realmUrl, context.getRequestPath()));
  }

  public Result authApp(Context context) {
    return Results.html().render(
        views.auth_app.template(appClientId, realmUrl));
  }

  public Result privacy_policy(Context context) {
    return Results.html().render(
        views.privacy_policy.template(context.getHostname()));
  }

  public Result forbidden() {
    return Results.forbidden().html().render(
        views.system.forbidden403.template());

  }

}
