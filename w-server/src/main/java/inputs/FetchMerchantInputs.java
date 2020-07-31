package inputs;

import static java.util.Objects.requireNonNull;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = FetchMerchantInputs.Builder.class)
public class FetchMerchantInputs {
  @NotNull
  @DecimalMin(value = "-90.0")
  @DecimalMax(value = "90.0")
  private final String lat;

  @NotNull
  @DecimalMin(value = "-180.0")
  @DecimalMax(value = "180.0")
  private final String lng;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(FetchMerchantInputs inputs) {
    return new Builder(inputs);
  }

  private FetchMerchantInputs(Builder builder) {
    this.lat = builder.lat;
    this.lng = builder.lng;
  }

  public String getLat() {
    return lat;
  }

  public String getLng() {
    return lng;
  }

  @Override
  public String toString() {
    return "FetchMerchantInputs [lat=" + lat + ", lng=" + lng + "]";
  }

  @JsonPOJOBuilder
  public static class Builder {
    private String lat;
    private String lng;

    private Builder() {}

    private Builder(FetchMerchantInputs inputs) {
      requireNonNull(inputs, "inputs cannot be null");
      this.lat = inputs.lat;
      this.lng = inputs.lng;
    }

    public Builder withLat(String lat) {
      this.lat = lat;
      return this;
    }

    public Builder withLng(String lng) {
      this.lng = lng;
      return this;
    }

    public FetchMerchantInputs build() {
      return new FetchMerchantInputs(this);
    }
  }
}
