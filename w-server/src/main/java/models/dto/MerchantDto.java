package models.dto;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import models.Merchant;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = MerchantDto.Builder.class)
public class MerchantDto {
  private final Long id;
  private final String name;
  private final String logo;
  private final String phoneNumber;
  private final boolean messaging;
  private final String distanceFrom;
  private final String latitude;
  private final String longitude;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Merchant merchant) {
    return new Builder(merchant);
  }

  public static Builder builder(MerchantDto merchant) {
    return new Builder(merchant);
  }

  private MerchantDto(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.logo = builder.logo;
    this.phoneNumber = builder.phoneNumber;
    this.messaging = builder.messaging;
    this.distanceFrom = builder.distanceFrom;
    this.latitude = builder.latitude;
    this.longitude = builder.longitude;
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

  @JsonPOJOBuilder
  public static class Builder {
    private Long id;
    private String name;
    private String logo;
    private String phoneNumber;
    private Boolean messaging;
    private String distanceFrom;
    private String latitude;
    private String longitude;

    private Builder() {}

    private Builder(Merchant merchant) {
      requireNonNull(merchant, "merchant cannot be null");
      this.id = merchant.getId();
      this.name = merchant.getName();
      this.logo = merchant.getLogo();
      this.phoneNumber = merchant.getPhoneNumber();
      this.messaging = merchant.getMessaging();
      this.latitude = String.format("%,.6f", merchant.getLatitude());
      this.longitude = String.format("%,.6f", merchant.getLongitude());
    }

    private Builder(MerchantDto merchant) {
      requireNonNull(merchant, "merchant cannot be null");
      this.id = merchant.id;
      this.name = merchant.name;
      this.logo = merchant.logo;
      this.phoneNumber = merchant.phoneNumber;
      this.messaging = merchant.messaging;
      this.distanceFrom = merchant.distanceFrom;
      this.latitude = merchant.latitude;
      this.longitude = merchant.longitude;
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

    public MerchantDto build() {
      requireNonNull(name, "name cannot be null");
      requireNonNull(logo, "logo cannot be null");
      requireNonNull(phoneNumber, "phoneNumber cannot be null");
      requireNonNull(messaging, "messaging cannot be null");
      requireNonNull(distanceFrom, "distanceFrom cannot be null");
      requireNonNull(latitude, "latitude cannot be null");
      requireNonNull(longitude, "longitude cannot be null");
      return new MerchantDto(this);
    }
  }
}
