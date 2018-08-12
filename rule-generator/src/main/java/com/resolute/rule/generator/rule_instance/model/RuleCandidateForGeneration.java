package com.resolute.rule.generator.rule_instance.model;

import java.util.List;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableList;

import static java.util.Objects.requireNonNull;

import jersey.repackaged.com.google.common.collect.Lists;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = RuleCandidateForGeneration.Builder.class)
public class RuleCandidateForGeneration {
  private final int id;
  private final String text;
  private final List<List<Integer>> pointIds;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(RuleCandidateForGeneration entityIndex) {
    return new Builder(entityIndex);
  }

  private RuleCandidateForGeneration(Builder builder) {
    this.id = builder.id;
    this.text = builder.text;
    this.pointIds = builder.pointIds;
  }

  public int getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  @JsonProperty("point_ids")
  public List<List<Integer>> getPointIds() {
    return pointIds;
  }

  @Override
  public String toString() {
    return "RuleCandidateForGeneration [id=" + id + ", text=" + text + ", pointIds=" + pointIds
        + "]";
  }



  @JsonPOJOBuilder
  public static class Builder {
    private Integer id;
    private String text;
    private List<List<Integer>> pointIds;


    private Builder() {}

    private Builder(RuleCandidateForGeneration entityIndex) {
      requireNonNull(entityIndex, "entityIndex cannot be null");
      this.id = entityIndex.id;
      this.text = entityIndex.text;
      this.pointIds = entityIndex.pointIds;
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

    @JsonProperty("point_ids")
    public Builder withPointIds(List<List<Integer>> pointIds) {
      requireNonNull(pointIds, "pointIds cannot be null");
      List<List<Integer>> p = Lists.newArrayList();
      for (List<Integer> item : pointIds) {
        if (item.size() == 1 && item.get(0) == null) {
          p.add(ImmutableList.of());
        } else {
          p.add(ImmutableList.copyOf(item));
        }
      }
      this.pointIds = ImmutableList.copyOf(p);
      return this;
    }

    public RuleCandidateForGeneration build() {
      requireNonNull(id, "id cannot be null");
      requireNonNull(text, "text cannot be null");
      requireNonNull(pointIds, "pointIds cannot be null");
      return new RuleCandidateForGeneration(this);
    }
  }
}
