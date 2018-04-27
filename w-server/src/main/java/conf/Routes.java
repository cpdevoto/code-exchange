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


package conf;


import controllers.ApplicationController;
import controllers.MerchantApiController;
import controllers.NoticeApiController;
import controllers.SearchApiController;
import controllers.UserApiController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;

public class Routes implements ApplicationRoutes {

  @Override
  public void init(Router router) {

    router.GET().route("/index.html").with(ApplicationController::index);
    router.GET().route("/auth").with(ApplicationController::auth);
    router.GET().route("/auth/app").with(ApplicationController::authApp);
    router.GET().route("/privacy-policy.html").with(ApplicationController::privacy_policy);
    router.GET().route("/forbidden.html").with(ApplicationController::forbidden);

    router.GET().route("/api/v1/users").with(UserApiController::users_index);
    router.GET().route("/api/v1/users/me/notices").with(NoticeApiController::user_notices);
    router.GET().route("/api/v1/users/me/merchants")
        .with(MerchantApiController::user_favorite_merchants);
    router.GET().route("/api/v1/merchants").with(MerchantApiController::merchants);
    router.GET().route("/api/v1/merchants/{id}").with(MerchantApiController::merchant);
    router.GET().route("/api/v1/merchants/{id}/notices")
        .with(NoticeApiController::merchant_notices);
    router.GET().route("/api/v1/search")
    .with(SearchApiController::search);


    ///////////////////////////////////////////////////////////////////////
    // Assets (pictures / javascript)
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
    router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);
    router.GET().route("/favicon.ico").with(AssetsController::serveStatic);

    ///////////////////////////////////////////////////////////////////////
    // Index / Catchall shows index page
    ///////////////////////////////////////////////////////////////////////
    router.GET().route("/.*").with(ApplicationController::index);



  }

}
