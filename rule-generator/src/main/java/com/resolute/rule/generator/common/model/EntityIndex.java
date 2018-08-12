package com.resolute.rule.generator.common.model;

import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import static java.util.Objects.requireNonNull;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = EntityIndex.Builder.class)
public class EntityIndex {
  private final int id;
  private final String text;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(EntityIndex entityIndex) {
    return new Builder(entityIndex);
  }

  private EntityIndex(Builder builder) {
    this.id = builder.id;
    this.text = builder.text;
  }

  public int getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private Integer id;
    private String text;

    private Builder() {}

    private Builder(EntityIndex entityIndex) {
      requireNonNull(entityIndex, "entityIndex cannot be null");
      this.id = entityIndex.id;
      this.text = entityIndex.text;
    }

    public Builder with(Consumer<Builder> consumer) {
      requireNonNull(consumer, "consumer cannot be null");
      consumer.accept(this);
      return this;
    }

    public Builder withId(int id) {
      this.id = id;
      return this;
    }

    public Builder withText(String text) {
      requireNonNull(text, "text cannot be null");
      this.text = text;
      return this;
    }

    public EntityIndex build() {
      requireNonNull(id, "id cannot be null");
      requireNonNull(text, "text cannot be null");
      return new EntityIndex(this);
    }
  }
}
