package inputs;

import static java.util.Objects.requireNonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = SearchInputs.Builder.class)
public class SearchInputs {
  @NotNull
  @Min(value = 1)
  private final String limit;

  @NotNull
  @NotBlank
  private final String search;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(SearchInputs inputs) {
    return new Builder(inputs);
  }

  private SearchInputs(Builder builder) {
    this.limit = builder.limit;
    this.search = builder.search;
  }

  public String getLimit() {
    return limit;
  }

  public String getSearch() {
    return search;
  }

  @Override
  public String toString() {
    return "SearchInputs [limit=" + limit + ", search=" + search + "]";
  }

  @JsonPOJOBuilder
  public static class Builder {
    private String limit = "10";
    private String search;

    private Builder() {}

    private Builder(SearchInputs inputs) {
      requireNonNull(inputs, "inputs cannot be null");
      this.limit = inputs.limit;
      this.search = inputs.search;
    }

    public Builder withLimit(String limit) {
      this.limit = limit;
      return this;
    }

    public Builder withSearch(String search) {
      this.search = search;
      return this;
    }

    public SearchInputs build() {
      return new SearchInputs(this);
    }
  }
}
