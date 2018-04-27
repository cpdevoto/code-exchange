package inputs;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = FetchUserNoticesInputs.Builder.class)
public class FetchUserNoticesInputs {
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

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(FetchUserNoticesInputs inputs) {
    return new Builder(inputs);
  }

  private FetchUserNoticesInputs(Builder builder) {
    this.lat = builder.lat;
    this.lng = builder.lng;
    this.limit = builder.limit;
    this.skip = builder.skip;
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

  @Override
  public String toString() {
    return "FetchUserNoticesInputs [lat=" + lat + ", lng=" + lng + ", limit=" + limit
        + ", skip=" + skip + "]";
  }

  @JsonPOJOBuilder
  public static class Builder {
    private String lat;
    private String lng;
    private String limit;
    private String skip;

    private Builder() {}

    private Builder(FetchUserNoticesInputs inputs) {
      requireNonNull(inputs, "inputs cannot be null");
      this.lat = inputs.lat;
      this.lng = inputs.lng;
      this.limit = inputs.limit;
      this.skip = inputs.skip;
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

    public FetchUserNoticesInputs build() {
      return new FetchUserNoticesInputs(this);
    }
  }
}
