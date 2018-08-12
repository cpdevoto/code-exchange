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

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = RuleTemplateInputPoint.Builder.class)
public class RuleTemplateInputPoint {
  private final int id;
  private final int templateId;
  private final String name;
  private final String description;
  private final String currentObjectExpression;
  private final boolean required;
  private final boolean array;
  private final int seqNumber;
  private final List<Tag> tags;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(RuleTemplateInputPoint ruleTemplateInputPoint) {
    return new Builder(ruleTemplateInputPoint);
  }

  private RuleTemplateInputPoint(Builder builder) {
    this.id = builder.id;
    this.templateId = builder.templateId;
    this.name = builder.name;
    this.description = builder.description;
    this.currentObjectExpression = builder.currentObjectExpression;
    this.required = builder.required;
    this.array = builder.array;
    this.seqNumber = builder.seqNumber;
    this.tags = builder.tags;
  }

  public int getId() {
    return id;
  }

  @JsonProperty("ad_rule_template_id")
  public int getTemplateId() {
    return templateId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @JsonProperty("current_object_expression")
  public String getCurrentObjectExpression() {
    return currentObjectExpression;
  }

  public boolean getRequired() {
    return required;
  }

  public boolean getArray() {
    return array;
  }

  @JsonProperty("seq_no")
  public int getSeqNumber() {
    return seqNumber;
  }

  public List<Tag> getTags() {
    return tags;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private Integer id;
    private Integer templateId;
    private String name;
    private String description;
    private String currentObjectExpression;
    private Boolean required;
    private Boolean array;
    private Integer seqNumber;
    private List<Tag> tags;

    private Builder() {}

    private Builder(RuleTemplateInputPoint ruleTemplateInputPoint) {
      requireNonNull(ruleTemplateInputPoint, "ruleTemplateInputPoint cannot be null");
      this.id = ruleTemplateInputPoint.id;
      this.templateId = ruleTemplateInputPoint.templateId;
      this.name = ruleTemplateInputPoint.name;
      this.description = ruleTemplateInputPoint.description;
      this.currentObjectExpression = ruleTemplateInputPoint.currentObjectExpression;
      this.required = ruleTemplateInputPoint.required;
      this.array = ruleTemplateInputPoint.array;
      this.seqNumber = ruleTemplateInputPoint.seqNumber;
      this.tags = ruleTemplateInputPoint.tags;
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

    @JsonProperty("ad_rule_template_id")
    public Builder withTemplateId(int templateId) {
      this.templateId = templateId;
      return this;
    }

    public Builder withName(String name) {
      requireNonNull(name, "name cannot be null");
      this.name = name;
      return this;
    }

    public Builder withDescription(String description) {
      requireNonNull(description, "description cannot be null");
      this.description = description;
      return this;
    }

    @JsonProperty("current_object_expression")
    public Builder withCurrentObjectExpression(String currentObjectExpression) {
      this.currentObjectExpression = currentObjectExpression;
      return this;
    }

    public Builder withRequired(boolean required) {
      this.required = required;
      return this;
    }

    public Builder withArray(boolean array) {
      this.array = array;
      return this;
    }

    @JsonProperty("seq_no")
    public Builder withSeqNumber(int seqNumber) {
      this.seqNumber = seqNumber;
      return this;
    }

    public Builder withTags(List<Tag> tags) {
      if (tags == null || tags.size() == 1 && tags.get(0) == null) {
        this.tags = ImmutableList.of();
      } else {
        this.tags = ImmutableList.copyOf(tags);
      }
      return this;
    }

    public RuleTemplateInputPoint build() {
      requireNonNull(id, "id cannot be null");
      requireNonNull(templateId, "templateId cannot be null");
      requireNonNull(name, "name cannot be null");
      requireNonNull(description, "description cannot be null");
      requireNonNull(required, "required cannot be null");
      requireNonNull(array, "array cannot be null");
      requireNonNull(seqNumber, "seqNumber cannot be null");
      requireNonNull(tags, "tags cannot be null");
      return new RuleTemplateInputPoint(this);
    }
  }
}
