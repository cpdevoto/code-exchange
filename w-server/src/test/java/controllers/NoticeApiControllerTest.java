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
import java.text.MessageFormat;
import java.util.List;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import models.dto.MerchantDto;
import models.dto.NoticeDto;

public class NoticeApiControllerTest extends AbstractControllerTest {

  private static final String URL_USER_NOTICES = "/api/v1/users/me/notices";
  private static final String URL_MERCHANT_NOTICES = "/api/v1/merchants/{0}/notices";

  @Test
  public void test_fetch_user_notices()
      throws JsonParseException, JsonMappingException, IOException {

    Response response = makeRequest(
        Request.GET().headers(PROTECTED_API_HEADERS).url(
            testServerUrl().path(URL_USER_NOTICES)
                .addQueryParameter("lat", "42.392430")
                .addQueryParameter("lng", "-83.455331")
                .addQueryParameter("limit", "10")
                .addQueryParameter("skip", "1")));

    assertThat(response.httpStatus, equalTo(200));

    List<NoticeDto> notices = response.payloadJsonAs(new TypeReference<List<NoticeDto>>() {});
    assertThat(notices, notNullValue());
    assertThat(notices.size(), equalTo(10));

    // Assert the values of the first notice
    NoticeDto notice = notices.get(0);
    assertNotice2(notice);
  }

  @Test
  public void test_fetch_merchant_notices()
      throws JsonParseException, JsonMappingException, IOException {

    Response response = makeRequest(
        Request.GET().headers(PROTECTED_API_HEADERS).url(
            testServerUrl().path(MessageFormat.format(URL_MERCHANT_NOTICES, "51336"))
                .addQueryParameter("lat", "42.392430")
                .addQueryParameter("lng", "-83.455331")
                .addQueryParameter("limit", "10")));

    assertThat(response.httpStatus, equalTo(200));

    List<NoticeDto> notices = response.payloadJsonAs(new TypeReference<List<NoticeDto>>() {});
    assertThat(notices, notNullValue());
    assertThat(notices.size(), equalTo(10));

    // Assert the values of the first notice
    NoticeDto notice = notices.get(0);
    assertNotice2(notice);
  }

  private void assertNotice2(NoticeDto notice) {
    assertThat(notice.getTitle(), equalTo("Rojo Informational Message to All #5"));
    assertThat(notice.getText(), equalTo("We are awesome"));
    assertThat(notice.getPhoto(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/998125.jpg"));
    assertThat(notice.getType(), equalTo("informational"));
    // Created At gives a different value depending on the timezone of the server where the tests
    // are run!
    assertThat(notice.getCreatedAt().getTime(),
        anyOf(equalTo(1515755107000L), equalTo(1515737107000L)));

    MerchantDto merchant = notice.getMerchant();
    assertThat(merchant.getId(), equalTo(51336L));
    assertThat(merchant.getName(), equalTo("Rojo Mexican Bistro"));
    assertThat(merchant.getLogo(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/111345.jpg"));
    assertThat(merchant.getPhoneNumber(), equalTo("(248) 792-6200"));
    assertThat(merchant.getMessaging(), equalTo(true));
    assertThat(merchant.getDistanceFrom(),
        equalTo("16.2"));
    assertThat(merchant.getLatitude(), equalTo("42.545368"));
    assertThat(merchant.getLongitude(), equalTo("-83.213715"));
  }

}
