package models.dto;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableList;

import models.Merchant;
import models.Tag;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = MerchantDetailsDto.Builder.class)
public class MerchantDetailsDto {
  private final long id;
  private final String name;
  private final String address1;
  private final String address2;
  private final String city;
  private final String state;
  private final String zip;
  private final String logo;
  private final String coverPhoto;
  private final String shortDescription;
  private final String longDescription;
  private final List<String> tags;
  private final String phoneNumber;
  private final boolean messaging;
  private final String distanceFrom;
  private final String latitude;
  private final String longitude;
  private final int insiders;
  private final Boolean isVIP;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Merchant merchant) {
    return new Builder(merchant);
  }

  public static Builder builder(MerchantDetailsDto merchant) {
    return new Builder(merchant);
  }

  private MerchantDetailsDto(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.address1 = builder.address1;
    this.address2 = builder.address2;
    this.city = builder.city;
    this.state = builder.state;
    this.zip = builder.zip;
    this.logo = builder.logo;
    this.coverPhoto = builder.coverPhoto;
    this.shortDescription = builder.shortDescription;
    this.longDescription = builder.longDescription;
    this.tags = builder.tags;
    this.phoneNumber = builder.phoneNumber;
    this.messaging = builder.messaging;
    this.distanceFrom = builder.distanceFrom;
    this.latitude = builder.latitude;
    this.longitude = builder.longitude;
    this.insiders = builder.insiders;
    this.isVIP = builder.isVIP;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLogo() {
    return logo;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public boolean getMessaging() {
    return messaging;
  }

  public String getDistanceFrom() {
    return distanceFrom;
  }

  public String getLatitude() {
    return latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public String getAddress1() {
    return address1;
  }

  public String getAddress2() {
    return address2;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }

  public String getCoverPhoto() {
    return coverPhoto;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public List<String> getTags() {
    return tags;
  }

  public int getInsiders() {
    return insiders;
  }

  public Boolean getIsVIP() {
    return isVIP;
  }



  @Override
  public String toString() {
    return "MerchantDetailsDto [id=" + id + ", name=" + name + ", address1=" + address1
        + ", address2=" + address2 + ", city=" + city + ", state=" + state + ", zip=" + zip
        + ", logo=" + logo + ", coverPhoto=" + coverPhoto + ", shortDescription=" + shortDescription
        + ", longDescription=" + longDescription + ", tags=" + tags + ", phoneNumber=" + phoneNumber
        + ", messaging=" + messaging + ", distanceFrom=" + distanceFrom + ", latitude=" + latitude
        + ", longitude=" + longitude + ", insiders=" + insiders + ", isVIP=" + isVIP + "]";
  }



  @JsonPOJOBuilder
  public static class Builder {
    private Long id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String logo;
    private String coverPhoto;
    private String shortDescription;
    private String longDescription;
    private List<String> tags;
    private String phoneNumber;
    private Boolean messaging;
    private String distanceFrom;
    private String latitude;
    private String longitude;
    private Integer insiders;
    private Boolean isVIP;

    private Builder() {}

    private Builder(Merchant merchant) {
      requireNonNull(merchant, "merchant cannot be null");
      this.id = merchant.getId();
      this.name = merchant.getName();
      this.address1 = merchant.getAddress().getAddress1();
      this.address2 = merchant.getAddress().getAddress2();
      this.city = merchant.getAddress().getCity();
      this.state = merchant.getAddress().getState();
      this.zip = merchant.getAddress().getZip();
      this.logo = merchant.getLogo();
      this.coverPhoto = merchant.getCoverPhoto();
      this.shortDescription = merchant.getShortDescription();
      this.longDescription = merchant.getLongDescription();
      this.tags = ImmutableList.copyOf(
          merchant.getMerchantTags().stream().map(Tag::getName).collect(Collectors.toList()));
      this.phoneNumber = merchant.getPhoneNumber();
      this.messaging = merchant.getMessaging();
      this.latitude = String.format("%,.6f", merchant.getLatitude());
      this.longitude = String.format("%,.6f", merchant.getLongitude());
      this.insiders = merchant.getMerchantFavoriteUsers().size();
    }

    private Builder(MerchantDetailsDto merchant) {
      requireNonNull(merchant, "merchant cannot be null");
      this.id = merchant.id;
      this.name = merchant.name;
      this.address1 = merchant.address1;
      this.address2 = merchant.address2;
      this.city = merchant.city;
      this.state = merchant.state;
      this.zip = merchant.zip;
      this.logo = merchant.logo;
      this.coverPhoto = merchant.coverPhoto;
      this.shortDescription = merchant.shortDescription;
      this.longDescription = merchant.longDescription;
      this.tags = merchant.tags;
      this.phoneNumber = merchant.phoneNumber;
      this.messaging = merchant.messaging;
      this.distanceFrom = merchant.distanceFrom;
      this.latitude = merchant.latitude;
      this.longitude = merchant.longitude;
      this.insiders = merchant.insiders;
      this.isVIP = merchant.isVIP;
    }

    public Builder with(Consumer<Builder> consumer) {
      requireNonNull(consumer, "consumer cannot be null");
      consumer.accept(this);
      return this;
    }

    public Builder withId(long id) {
      this.id = id;
      return this;
    }

    public Builder withName(String name) {
      requireNonNull(name, "name cannot be null");
      this.name = name;
      return this;
    }

    public Builder withLogo(String logo) {
      requireNonNull(logo, "logo cannot be null");
      this.logo = logo;
      return this;
    }

    public Builder withPhoneNumber(String phoneNumber) {
      requireNonNull(phoneNumber, "phoneNumber cannot be null");
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Builder withMessaging(boolean messaging) {
      this.messaging = messaging;
      return this;
    }

    public Builder withDistanceFrom(String distanceFrom) {
      requireNonNull(distanceFrom, "distanceFrom cannot be null");
      this.distanceFrom = distanceFrom;
      return this;
    }

    public Builder withLatitude(String latitude) {
      requireNonNull(latitude, "latitude cannot be null");
      this.latitude = latitude;
      return this;
    }

    public Builder withLongitude(String longitude) {
      requireNonNull(longitude, "longitude cannot be null");
      this.longitude = longitude;
      return this;
    }

    public Builder withAddress1(String address1) {
      requireNonNull(address1, "address1 cannot be null");
      this.address1 = address1;
      return this;
    }

    public Builder withAddress2(String address2) {
      this.address2 = address2;
      return this;
    }

    public Builder withCity(String city) {
      requireNonNull(city, "city cannot be null");
      this.city = city;
      return this;
    }

    public Builder withState(String state) {
      requireNonNull(state, "state cannot be null");
      this.state = state;
      return this;
    }

    public Builder withZip(String zip) {
      requireNonNull(zip, "zip cannot be null");
      this.zip = zip;
      return this;
    }

    public Builder withCoverPhoto(String coverPhoto) {
      requireNonNull(coverPhoto, "coverPhoto cannot be null");
      this.coverPhoto = coverPhoto;
      return this;
    }

    public Builder withShortDescription(String shortDescription) {
      requireNonNull(shortDescription, "shortDescription cannot be null");
      this.shortDescription = shortDescription;
      return this;
    }

    public Builder withLongDescription(String longDescription) {
      requireNonNull(longDescription, "longDescription cannot be null");
      this.longDescription = longDescription;
      return this;
    }

    public Builder withTags(List<String> tags) {
      requireNonNull(tags, "tags cannot be null");
      this.tags = ImmutableList.copyOf(tags);
      return this;
    }

    public Builder withInsiders(int insiders) {
      this.insiders = insiders;
      return this;
    }

    public Builder withIsVIP(boolean isVIP) {
      this.isVIP = isVIP;
      return this;
    }

    public MerchantDetailsDto build() {
      requireNonNull(name, "name cannot be null");
      requireNonNull(logo, "logo cannot be null");
      requireNonNull(phoneNumber, "phoneNumber cannot be null");
      requireNonNull(messaging, "messaging cannot be null");
      requireNonNull(distanceFrom, "distanceFrom cannot be null");
      requireNonNull(latitude, "latitude cannot be null");
      requireNonNull(longitude, "longitude cannot be null");
      requireNonNull(address1, "address1 cannot be null");
      requireNonNull(city, "city cannot be null");
      requireNonNull(state, "state cannot be null");
      requireNonNull(zip, "zip cannot be null");
      requireNonNull(coverPhoto, "coverPhoto cannot be null");
      requireNonNull(shortDescription, "shortDescription cannot be null");
      requireNonNull(longDescription, "longDescription cannot be null");
      requireNonNull(tags, "tags cannot be null");
      requireNonNull(insiders, "insiders cannot be null");
      return new MerchantDetailsDto(this);
    }
  }
}
