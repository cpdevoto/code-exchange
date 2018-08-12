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
@JsonDeserialize(builder = RuleTemplateOutputPoint.Builder.class)
public class RuleTemplateOutputPoint {
  private final int id;
  private final int templateId;
  private final String description;
  private final int dataTypeId;
  private final int unitId;
  private final String range;
  private final int seqNumber;
  private final List<Tag> tags;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(RuleTemplateOutputPoint ruleTemplateOutputPoint) {
    return new Builder(ruleTemplateOutputPoint);
  }

  private RuleTemplateOutputPoint(Builder builder) {
    this.id = builder.id;
    this.templateId = builder.templateId;
    this.description = builder.description;
    this.dataTypeId = builder.dataTypeId;
    this.unitId = builder.unitId;
    this.range = builder.range;
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

  public String getDescription() {
    return description;
  }

  @JsonProperty("data_type_id")
  public int getDataTypeId() {
    return dataTypeId;
  }

  @JsonProperty("unit_id")
  public int getUnitId() {
    return unitId;
  }

  public String getRange() {
    return range;
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
    private String description;
    private Integer dataTypeId;
    private Integer unitId;
    private String range;
    private Integer seqNumber;
    private List<Tag> tags;

    private Builder() {}

    private Builder(RuleTemplateOutputPoint ruleTemplateOutputPoint) {
      requireNonNull(ruleTemplateOutputPoint, "ruleTemplateOutputPoint cannot be null");
      this.id = ruleTemplateOutputPoint.id;
      this.templateId = ruleTemplateOutputPoint.templateId;
      this.description = ruleTemplateOutputPoint.description;
      this.dataTypeId = ruleTemplateOutputPoint.dataTypeId;
      this.unitId = ruleTemplateOutputPoint.unitId;
      this.range = ruleTemplateOutputPoint.range;
      this.seqNumber = ruleTemplateOutputPoint.seqNumber;
      this.tags = ruleTemplateOutputPoint.tags;
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

    public Builder withDescription(String description) {
      requireNonNull(description, "description cannot be null");
      this.description = description;
      return this;
    }

    @JsonProperty("data_type_id")
    public Builder withDataTypeId(int dataTypeId) {
      this.dataTypeId = dataTypeId;
      return this;
    }

    @JsonProperty("unit_id")
    public Builder withUnitId(int unitId) {
      this.unitId = unitId;
      return this;
    }

    public Builder withRange(String range) {
      requireNonNull(range, "range cannot be null");
      this.range = range;
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

    public RuleTemplateOutputPoint build() {
      requireNonNull(id, "id cannot be null");
      requireNonNull(templateId, "templateId cannot be null");
      requireNonNull(description, "description cannot be null");
      requireNonNull(dataTypeId, "dataTypeId cannot be null");
      requireNonNull(unitId, "unitId cannot be null");
      requireNonNull(seqNumber, "seqNumber cannot be null");
      requireNonNull(tags, "tags cannot be null");
      return new RuleTemplateOutputPoint(this);
    }
  }
}
