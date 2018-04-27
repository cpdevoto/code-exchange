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
import java.util.Set;
import java.util.stream.Collectors;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import models.dto.MerchantDetailsDto;

public class MerchantApiControllerTest extends AbstractControllerTest {

  private static final String URL_MERCHANTS = "/api/v1/merchants";
  private static final String URL_MERCHANT = "/api/v1/merchants/{0}";
  private static final String URL_USER_FAVORITE_MERCHANTS = "/api/v1/users/me/merchants";

  @Test
  public void test_fetch_merchants()
      throws JsonParseException, JsonMappingException, IOException {

    Response response = makeRequest(
        Request.GET().headers(PROTECTED_API_HEADERS).url(
            testServerUrl().path(URL_MERCHANTS)
                .addQueryParameter("search", "Res")
                .addQueryParameter("radius", "16.23")
                .addQueryParameter("lat", "42.392430")
                .addQueryParameter("lng", "-83.455331")
                .addQueryParameter("limit", "1")
                .addQueryParameter("skip", "1")));

    assertThat(response.httpStatus, equalTo(200));

    List<MerchantDetailsDto> merchants =
        response.payloadJsonAs(new TypeReference<List<MerchantDetailsDto>>() {});
    assertThat(merchants, notNullValue());
    assertThat(merchants.size(), equalTo(1));

    assertMerchant3(merchants.get(0), false);
  }

  @Test
  public void test_fetch_merchant()
      throws JsonParseException, JsonMappingException, IOException {

    Response response = makeRequest(
        Request.GET().headers(PROTECTED_API_HEADERS).url(
            testServerUrl().path(MessageFormat.format(URL_MERCHANT, "51335"))
                .addQueryParameter("lat", "42.392430")
                .addQueryParameter("lng", "-83.455331")));

    assertThat(response.httpStatus, equalTo(200));
    MerchantDetailsDto merchant =
        response.payloadJsonAs(MerchantDetailsDto.class);
    assertThat(merchant, notNullValue());

    assertMerchant1(merchant, false);
  }

  @Test
  public void test_fetch_user_favorite_merchants()
      throws JsonParseException, JsonMappingException, IOException {

    Response response = makeRequest(
        Request.GET().headers(PROTECTED_API_HEADERS).url(
            testServerUrl().path(URL_USER_FAVORITE_MERCHANTS)
                .addQueryParameter("lat", "42.392430")
                .addQueryParameter("lng", "-83.455331")
                .addQueryParameter("limit", "1")
                .addQueryParameter("skip", "1")));

    assertThat(response.httpStatus, equalTo(200));

    List<MerchantDetailsDto> merchants =
        response.payloadJsonAs(new TypeReference<List<MerchantDetailsDto>>() {});
    assertThat(merchants, notNullValue());
    assertThat(merchants.size(), equalTo(1));

    assertMerchant1(merchants.get(0), true);
  }

  private void assertMerchant1(MerchantDetailsDto merchant, boolean assertVip) {
    assertThat(merchant.getId(), equalTo(51335L));
    assertThat(merchant.getName(), equalTo("Woolly & Co."));
    assertThat(merchant.getAddress1(), equalTo("147 Pierce St"));
    assertThat(merchant.getAddress2(), nullValue());
    assertThat(merchant.getCity(), equalTo("Birmingham"));
    assertThat(merchant.getState(), equalTo("MI"));
    assertThat(merchant.getZip(), equalTo("48009"));
    assertThat(merchant.getLogo(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/000345.jpg"));
    assertThat(merchant.getCoverPhoto(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/000678.jpg"));
    assertThat(merchant.getShortDescription(), equalTo("Yarn store"));
    assertThat(merchant.getLongDescription(),
        equalTo("Best darn yarn store on all of Pierce Street"));
    assertThat(merchant.getTags(), notNullValue());
    assertThat(merchant.getTags().size(), equalTo(2));
    Set<String> tags = merchant.getTags().stream().collect(Collectors.toSet());
    assertThat(tags.contains("retailer"), equalTo(true));
    assertThat(tags.contains("yarn"), equalTo(true));
    assertThat(merchant.getPhoneNumber(), equalTo("(248) 480-4354"));
    assertThat(merchant.getMessaging(), equalTo(true));
    assertThat(merchant.getDistanceFrom(), equalTo("16.2"));
    assertThat(merchant.getLatitude(), equalTo("42.546242"));
    assertThat(merchant.getLongitude(), equalTo("-83.214674"));
    assertThat(merchant.getInsiders(), equalTo(2));
    if (assertVip) {
      assertThat(merchant.getIsVIP(), equalTo(true));
    }
  }

  private void assertMerchant3(MerchantDetailsDto merchant, boolean assertVip) {
    assertThat(merchant.getId(), equalTo(51337L));
    assertThat(merchant.getName(), equalTo("Flemings Prime Steakhouse"));
    assertThat(merchant.getAddress1(), equalTo("323 N Old Woodward Ave"));
    assertThat(merchant.getAddress2(), nullValue());
    assertThat(merchant.getCity(), equalTo("Birmingham"));
    assertThat(merchant.getState(), equalTo("MI"));
    assertThat(merchant.getZip(), equalTo("48009"));
    assertThat(merchant.getLogo(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/222345.jpg"));
    assertThat(merchant.getCoverPhoto(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/222678.jpg"));
    assertThat(merchant.getShortDescription(), equalTo("Prime steakhouse"));
    assertThat(merchant.getLongDescription(),
        equalTo("Best darn prime steakhouse on all of N. Old Woodward Avenue"));
    assertThat(merchant.getTags(), notNullValue());
    assertThat(merchant.getTags().size(), equalTo(2));
    Set<String> tags = merchant.getTags().stream().collect(Collectors.toSet());
    assertThat(tags.contains("restaurant"), equalTo(true));
    assertThat(tags.contains("steak"), equalTo(true));
    assertThat(merchant.getPhoneNumber(), equalTo("(248) 723-0134"));
    assertThat(merchant.getMessaging(), equalTo(true));
    assertThat(merchant.getDistanceFrom(), equalTo("16.2"));
    assertThat(merchant.getLatitude(), equalTo("42.547887"));
    assertThat(merchant.getLongitude(), equalTo("-83.216100"));
    assertThat(merchant.getInsiders(), equalTo(0));
    if (assertVip) {
      assertThat(merchant.getIsVIP(), equalTo(false));
    }
  }

}
