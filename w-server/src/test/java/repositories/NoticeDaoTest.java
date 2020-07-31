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

package repositories;


import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import inputs.FetchMerchantNoticesInputs;
import inputs.FetchUserNoticesInputs;
import models.dto.MerchantDto;
import models.dto.NoticeDto;
import models.dto.NoticeScope;

public class NoticeDaoTest extends AbstractDaoTest {

  @Test
  public void test_fetch_user_notices() throws JsonProcessingException {
    long userId = 51335L;
    Set<Long> favorites = ImmutableSet.of(51335L, 51336L);
    Set<Long> vips = ImmutableSet.of(51335L);

    FetchUserNoticesInputs inputs = FetchUserNoticesInputs.builder()
        .withLat("42.392430")
        .withLng("-83.455331")
        .withLimit("10")
        .build();

    List<NoticeDto> notices =
        repo.notices().fetch_user_notices(userId, inputs);
    assertThat(notices, notNullValue());
    assertThat(notices.size(), equalTo(10));

    // Confirm that the records are sorted in descending order by the created at timestamp
    long lastTime = notices.get(0).getCreatedAt().getTime();
    for (int i = 1; i < notices.size(); i++) {
      long time = notices.get(i).getCreatedAt().getTime();
      assertThat(time, lessThanOrEqualTo(lastTime));
    }



    // Confirm that the only VIP-scoped messages are from merchants in the user's vip list, and that
    // all notices are from merchants in the favorites list
    for (NoticeDto notice : notices) {
      assertThat(favorites.contains(notice.getMerchant().getId()), equalTo(true));
      assertThat(notice.getScopeId() == NoticeScope.ALL_USERS.getId()
          || vips.contains(notice.getMerchant().getId()),
          equalTo(true));
    }

    // Assert the values of the first notice
    NoticeDto notice = notices.get(0);
    assertNotice1(notice);

    inputs = FetchUserNoticesInputs.builder(inputs)
        .withSkip(Optional.of("1"))
        .build();

    // Test offset
    notices =
        repo.notices().fetch_user_notices(userId, inputs);

    assertThat(notices, notNullValue());
    assertThat(notices.size(), equalTo(10));

    notice = notices.get(0);
    assertNotice2(notice);

    // Test that the user which created the merchant gets all notices for that merchant even if the
    // merchant is not on the user's favorite list or vip list.
    inputs = FetchUserNoticesInputs.builder(inputs)
        .withSkip(Optional.empty())
        .build();

    notices =
        repo.notices().fetch_user_notices(51336L, inputs);

    assertThat(notices, notNullValue());
    assertThat(notices.size(), equalTo(10));

    for (NoticeDto n : notices) {
      assertThat(n.getMerchant().getId(), equalTo(51335L));
    }

  }

  @Test
  public void test_fetch_merchant_notices() throws JsonProcessingException {
    long userId = 51335L;

    FetchMerchantNoticesInputs inputs = FetchMerchantNoticesInputs.builder()
        .withLat("42.392430")
        .withLng("-83.455331")
        .withLimit("10")
        .build();

    // Test that, if the user is a VIP for the merchant, all messages are returned
    List<NoticeDto> notices =
        repo.notices().fetch_merchant_notices(userId, 51335L, inputs);
    assertThat(notices, notNullValue());
    assertThat(notices.size(), equalTo(10));

    // Confirm that the records are sorted in descending order by the created at timestamp
    long lastTime = notices.get(0).getCreatedAt().getTime();
    for (int i = 1; i < notices.size(); i++) {
      long time = notices.get(i).getCreatedAt().getTime();
      assertThat(time, lessThanOrEqualTo(lastTime));
    }
    // Confirm that the VIP-scoped messages are returned
    assertThat(notices.stream().filter(n -> n.getScopeId() == NoticeScope.VIP_USERS.getId())
        .collect(Collectors.toSet()).isEmpty(), equalTo(false));

    // Assert the values of the first notice
    assertNotice1(notices.get(0));

    System.out.println(new ObjectMapper().writeValueAsString(notices));
    // Test offset
    inputs = FetchMerchantNoticesInputs.builder(inputs)
        .withSkip(Optional.of("1"))
        .build();

    notices =
        repo.notices().fetch_merchant_notices(userId, 51335L, inputs);
    assertThat(notices, notNullValue());
    assertThat(notices.size(), equalTo(10));

    assertThat(notices.get(0).getTitle(), equalTo("Woolly Informational Message to All #5"));

    // Test that, if the user is not a VIP for the merchant, no VIP messages are returned
    inputs = FetchMerchantNoticesInputs.builder(inputs)
        .withSkip(Optional.empty())
        .build();

    // Test that, if the user is not a VIP for the merchant, no VIP messages are returned
    notices =
        repo.notices().fetch_merchant_notices(userId, 51336L, inputs);
    assertThat(notices, notNullValue());
    assertThat(notices.size(), equalTo(10));

    // Confirm that no VIP-scoped messages are returned
    assertThat(notices.stream().filter(n -> n.getScopeId() == NoticeScope.VIP_USERS.getId())
        .collect(Collectors.toSet()).isEmpty(), equalTo(true));

    // Assert the values of the first notice
    assertNotice2(notices.get(0));

    // Test that, if the user is created the merchant, all messages are returned
    notices =
        repo.notices().fetch_merchant_notices(51336L, 51335L, inputs);

    assertThat(notices, notNullValue());
    assertThat(notices.size(), equalTo(10));

    // Confirm that the VIP-scoped messages are returned
    assertThat(notices.stream().filter(n -> n.getScopeId() == NoticeScope.VIP_USERS.getId())
        .collect(Collectors.toSet()).isEmpty(), equalTo(false));

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

  private void assertNotice1(NoticeDto notice) {
    assertThat(notice.getTitle(), equalTo("Woolly Informational Message to VIPs #5"));
    assertThat(notice.getText(), equalTo("We are awesome"));
    assertThat(notice.getPhoto(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/999130.jpg"));
    assertThat(notice.getType(), equalTo("informational"));
    // Created At gives a different value depending on the timezone of the server where the tests
    // are run!
    assertThat(notice.getCreatedAt().getTime(),
        anyOf(equalTo(1515758706000L), equalTo(1515740706000L)));

    MerchantDto merchant = notice.getMerchant();
    assertThat(merchant.getId(), equalTo(51335L));
    assertThat(merchant.getName(), equalTo("Woolly & Co."));
    assertThat(merchant.getLogo(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/000345.jpg"));
    assertThat(merchant.getPhoneNumber(), equalTo("(248) 480-4354"));
    assertThat(merchant.getMessaging(), equalTo(true));
    assertThat(merchant.getDistanceFrom(), equalTo("16.2"));
    assertThat(merchant.getLatitude(), equalTo("42.546242"));
    assertThat(merchant.getLongitude(), equalTo("-83.214674"));
  }
}
