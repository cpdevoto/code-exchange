package com.resolute.rule.generator.rule_instance.model;

import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import static java.util.Objects.requireNonNull;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = RuleTemplateInputConst.Builder.class)
public class RuleTemplateInputConst {
  private final int id;
  private final int templateId;
  private final String name;
  private final String description;
  private final int dataTypeId;
  private final int unitId;
  private final String defaultValue;
  private final boolean required;
  private final int seqNumber;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(RuleTemplateInputConst ruleTemplateInputConst) {
    return new Builder(ruleTemplateInputConst);
  }

  private RuleTemplateInputConst(Builder builder) {
    this.id = builder.id;
    this.templateId = builder.templateId;
    this.name = builder.name;
    this.description = builder.description;
    this.dataTypeId = builder.dataTypeId;
    this.unitId = builder.unitId;
    this.defaultValue = builder.defaultValue;
    this.required = builder.required;
    this.seqNumber = builder.seqNumber;
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

  @JsonProperty("data_type_id")
  public int getDataTypeId() {
    return dataTypeId;
  }

  @JsonProperty("unit_id")
  public int getUnitId() {
    return unitId;
  }

  @JsonProperty("default_value")
  public String getDefaultValue() {
    return defaultValue;
  }

  @JsonProperty("is_required")
  public boolean getRequired() {
    return required;
  }

  @JsonProperty("seq_no")
  public int getSeqNumber() {
    return seqNumber;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private Integer id;
    private Integer templateId;
    private String name;
    private String description;
    private Integer dataTypeId;
    private Integer unitId;
    private String defaultValue;
    private Boolean required;
    private Integer seqNumber;

    private Builder() {}

    private Builder(RuleTemplateInputConst ruleTemplateInputConst) {
      requireNonNull(ruleTemplateInputConst, "ruleTemplateInputConst cannot be null");
      this.id = ruleTemplateInputConst.id;
      this.templateId = ruleTemplateInputConst.templateId;
      this.name = ruleTemplateInputConst.name;
      this.description = ruleTemplateInputConst.description;
      this.dataTypeId = ruleTemplateInputConst.dataTypeId;
      this.unitId = ruleTemplateInputConst.unitId;
      this.defaultValue = ruleTemplateInputConst.defaultValue;
      this.required = ruleTemplateInputConst.required;
      this.seqNumber = ruleTemplateInputConst.seqNumber;
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

    @JsonProperty("default_value")
    public Builder withDefaultValue(String defaultValue) {
      requireNonNull(defaultValue, "defaultValue cannot be null");
      this.defaultValue = defaultValue;
      return this;
    }

    @JsonProperty("is_required")
    public Builder withRequired(boolean required) {
      this.required = required;
      return this;
    }

    @JsonProperty("seq_no")
    public Builder withSeqNumber(int seqNumber) {
      this.seqNumber = seqNumber;
      return this;
    }

    public RuleTemplateInputConst build() {
      requireNonNull(id, "id cannot be null");
      requireNonNull(templateId, "templateId cannot be null");
      requireNonNull(name, "name cannot be null");
      requireNonNull(description, "description cannot be null");
      requireNonNull(dataTypeId, "dataTypeId cannot be null");
      requireNonNull(unitId, "unitId cannot be null");
      requireNonNull(defaultValue, "defaultValue cannot be null");
      requireNonNull(required, "required cannot be null");
      requireNonNull(seqNumber, "seqNumber cannot be null");
      return new RuleTemplateInputConst(this);
    }
  }
}
