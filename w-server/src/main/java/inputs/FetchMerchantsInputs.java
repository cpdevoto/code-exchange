package inputs;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = FetchMerchantsInputs.Builder.class)
public class FetchMerchantsInputs {
  @NotNull
  @DecimalMin(value = "-90.0")
  @DecimalMax(value = "90.0")
  private final String lat;

  @NotNull
  @DecimalMin(value = "-180.0")
  @DecimalMax(value = "180.0")
  private final String lng;

  @NotNull
  @Min(value = 1)
  private final String limit;

  @Min(value = 1)
  private final String skip;

  @NotNull
  @DecimalMin(value = "0.0")
  private final String radius;

  @NotNull
  @NotBlank
  private final String search;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(FetchMerchantsInputs inputs) {
    return new Builder(inputs);
  }

  private FetchMerchantsInputs(Builder builder) {
    this.lat = builder.lat;
    this.lng = builder.lng;
    this.limit = builder.limit;
    this.skip = builder.skip;
    this.radius = builder.radius;
    this.search = builder.search;
  }

  public String getLat() {
    return lat;
  }

  public String getLng() {
    return lng;
  }

  public String getLimit() {
    return limit;
  }

  public Optional<String> getSkip() {
    return Optional.ofNullable(skip);
  }

  public String getRadius() {
    return radius;
  }

  public String getSearch() {
    return search;
  }

  @Override
  public String toString() {
    return "FetchMerchantsInputs [lat=" + lat + ", lng=" + lng + ", limit=" + limit + ", skip="
        + skip + ", radius=" + radius + ", search=" + search + "]";
  }

  @JsonPOJOBuilder
  public static class Builder {
    private String lat;
    private String lng;
    private String limit;
    private String skip;
    private String radius;
    private String search;

    private Builder() {}

    private Builder(FetchMerchantsInputs inputs) {
      requireNonNull(inputs, "inputs cannot be null");
      this.lat = inputs.lat;
      this.lng = inputs.lng;
      this.limit = inputs.limit;
      this.skip = inputs.skip;
      this.radius = inputs.radius;
      this.search = inputs.search;
    }

    public Builder withLat(String lat) {
      this.lat = lat;
      return this;
    }

    public Builder withLng(String lng) {
      this.lng = lng;
      return this;
    }

    public Builder withLimit(String limit) {
      this.limit = limit;
      return this;
    }

    public Builder withSkip(Optional<String> skip) {
      requireNonNull(skip, "skip cannot be null");
      this.skip = skip.orElseGet(() -> null);
      return this;
    }

    public Builder withRadius(String radius) {
      this.radius = radius;
      return this;
    }

    public Builder withSearch(String search) {
      this.search = search;
      return this;
    }

    public FetchMerchantsInputs build() {
      return new FetchMerchantsInputs(this);
    }
  }
}
