package repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import inputs.FetchMerchantsInputs;
import inputs.FetchUserFavoriteMerchantsInputs;
import models.dto.MerchantDetailsDto;

public class MerchantDaoTest extends AbstractDaoTest {

  @Test
  public void test_fetch_merchants() {

    FetchMerchantsInputs inputs = FetchMerchantsInputs.builder()
        .withLat("42.392430")
        .withLng("-83.455331")
        .withLimit("1")
        .withRadius("16.23")
        .withSearch("res")
        .build();

    List<MerchantDetailsDto> merchants = repo.merchants().fetch_merchants(inputs);

    assertThat(merchants, notNullValue());
    assertThat(merchants.size(), equalTo(1));

    MerchantDetailsDto merchant = merchants.get(0);

    assertMerchant2(merchant, false);

    inputs = FetchMerchantsInputs.builder(inputs)
        .withSkip(Optional.of("1"))
        .build();

    merchants = repo.merchants().fetch_merchants(inputs);

    assertThat(merchants, notNullValue());
    assertThat(merchants.size(), equalTo(1));

    merchant = merchants.get(0);

    assertMerchant3(merchant, false);
  }


  @Test
  public void test_fetch_user_favorite_merchants() {
    long userId = 51335L;
    FetchUserFavoriteMerchantsInputs inputs = FetchUserFavoriteMerchantsInputs.builder()
        .withLat("42.392430")
        .withLng("-83.455331")
        .withLimit("1")
        .build();

    List<MerchantDetailsDto> merchants = repo.merchants().fetch_user_favorite_merchants(userId,
        inputs);

    assertThat(merchants, notNullValue());
    assertThat(merchants.size(), equalTo(1));

    MerchantDetailsDto merchant = merchants.get(0);

    assertMerchant2(merchant, true);

    inputs = FetchUserFavoriteMerchantsInputs.builder(inputs)
        .withSkip(Optional.of("1"))
        .build();

    merchants = repo.merchants().fetch_user_favorite_merchants(userId,
        inputs);

    assertThat(merchants, notNullValue());
    assertThat(merchants.size(), equalTo(1));

    merchant = merchants.get(0);

    assertMerchant1(merchant, true);
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

  private void assertMerchant2(MerchantDetailsDto merchant, boolean assertVip) {
    assertThat(merchant.getId(), equalTo(51336L));
    assertThat(merchant.getName(), equalTo("Rojo Mexican Bistro"));
    assertThat(merchant.getAddress1(), equalTo("250 E Merrill St"));
    assertThat(merchant.getAddress2(), nullValue());
    assertThat(merchant.getCity(), equalTo("Birmingham"));
    assertThat(merchant.getState(), equalTo("MI"));
    assertThat(merchant.getZip(), equalTo("48009"));
    assertThat(merchant.getLogo(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/111345.jpg"));
    assertThat(merchant.getCoverPhoto(),
        equalTo("http://bucket.s3-us-east-2a.amazonaws.com/wantify/111678.jpg"));
    assertThat(merchant.getShortDescription(), equalTo("Mexican bistro"));
    assertThat(merchant.getLongDescription(),
        equalTo("Best darn Mexican bistro on all of E. Merrill Street"));
    assertThat(merchant.getTags(), notNullValue());
    assertThat(merchant.getTags().size(), equalTo(2));
    Set<String> tags = merchant.getTags().stream().collect(Collectors.toSet());
    assertThat(tags.contains("restaurant"), equalTo(true));
    assertThat(tags.contains("mexican"), equalTo(true));
    assertThat(merchant.getPhoneNumber(), equalTo("(248) 792-6200"));
    assertThat(merchant.getMessaging(), equalTo(true));
    assertThat(merchant.getDistanceFrom(), equalTo("16.2"));
    assertThat(merchant.getLatitude(), equalTo("42.545368"));
    assertThat(merchant.getLongitude(), equalTo("-83.213715"));
    assertThat(merchant.getInsiders(), equalTo(2));
    if (assertVip) {
      assertThat(merchant.getIsVIP(), equalTo(false));
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
