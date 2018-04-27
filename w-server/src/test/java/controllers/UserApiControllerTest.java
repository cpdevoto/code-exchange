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


import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.containsString;

public class UserApiControllerTest extends AbstractControllerTest {

  private static final String URL_USERS_INDEX = "/api/v1/users";

  @Test
  public void test_fetch_users_index() {

    Response response = makeRequest(
        Request.GET().headers(PROTECTED_API_HEADERS).url(
            testServerUrl().path(URL_USERS_INDEX)));

    assertThat(response.payload, containsString("cpdevoto@gmail.com"));

  }

}
