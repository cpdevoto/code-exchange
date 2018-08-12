package com.resolute.rule.generator.rule_instance.model;

import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import static java.util.Objects.requireNonNull;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = Tag.Builder.class)
public class Tag {
  private final int id;
  private final String name;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(Tag tag) {
    return new Builder(tag);
  }

  private Tag(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private Integer id;
    private String name;

    private Builder() {}

    private Builder(Tag tag) {
      requireNonNull(tag, "tag cannot be null");
      this.id = tag.id;
      this.name = tag.name;
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

    public Builder withName(String name) {
      requireNonNull(name, "name cannot be null");
      this.name = name;
      return this;
    }

    public Tag build() {
      requireNonNull(id, "id cannot be null");
      requireNonNull(name, "name cannot be null");
      return new Tag(this);
    }
  }
}
