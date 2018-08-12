package com.resolute.rule.generator.common.model;

import java.util.List;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableList;

import static java.util.Objects.requireNonNull;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = RuleInstances.Builder.class)
public class RuleInstances {
  private final List<EntityIndex> equipment;
  private final List<EntityIndex> candidates;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(RuleInstances ruleInstances) {
    return new Builder(ruleInstances);
  }

  private RuleInstances(Builder builder) {
    this.equipment = builder.equipment;
    this.candidates = builder.candidates;
  }

  public List<EntityIndex> getEquipment() {
    return equipment;
  }

  public List<EntityIndex> getCandidates() {
    return candidates;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private List<EntityIndex> equipment;
    private List<EntityIndex> candidates;

    private Builder() {}

    private Builder(RuleInstances ruleInstances) {
      requireNonNull(ruleInstances, "ruleInstances cannot be null");
      this.equipment = ruleInstances.equipment;
      this.candidates = ruleInstances.candidates;
    }

    public Builder with(Consumer<Builder> consumer) {
      requireNonNull(consumer, "consumer cannot be null");
      consumer.accept(this);
      return this;
    }

    public Builder withEquipment(List<EntityIndex> equipment) {
      requireNonNull(equipment, "equipment cannot be null");
      this.equipment = ImmutableList.copyOf(equipment);
      return this;
    }

    public Builder withCandidates(List<EntityIndex> candidates) {
      requireNonNull(candidates, "candidates cannot be null");
      this.candidates = ImmutableList.copyOf(candidates);
      return this;
    }

    public RuleInstances build() {
      requireNonNull(equipment, "equipment cannot be null");
      requireNonNull(candidates, "candidates cannot be null");
      return new RuleInstances(this);
    }
  }
}
