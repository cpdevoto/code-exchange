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


import java.io.IOException;
import java.util.List;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SearchApiControllerTest extends AbstractControllerTest {

  private static final String URL_SEARCH = "/api/v1/search";

  @Test
  public void test_fetch_user_notices()
      throws JsonParseException, JsonMappingException, IOException {

    Response response = makeRequest(
        Request.GET().headers(PROTECTED_API_HEADERS).url(
            testServerUrl().path(URL_SEARCH)
                .addQueryParameter("value", "R")
                .addQueryParameter("limit", "3")));

    assertThat(response.httpStatus, equalTo(200));

    List<String> results = response.payloadJsonAs(new TypeReference<List<String>>() {});
    assertThat(results, notNullValue());
    assertThat(results.size(), equalTo(3));

    assertThat(results.get(0), equalTo("restaurant"));
    assertThat(results.get(1), equalTo("retailer"));
    assertThat(results.get(2), equalTo("Rojo Mexican Bistro"));
  }

}
