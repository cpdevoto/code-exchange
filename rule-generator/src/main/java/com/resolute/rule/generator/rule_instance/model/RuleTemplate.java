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
@JsonDeserialize(builder = RuleTemplate.Builder.class)
public class RuleTemplate {
  private final int id;
  private final String faultNumber;
  private final String name;
  private final String description;
  private final String nodeFilterExpression;
  private final boolean active;
  private final List<Tag> templateTags;
  private final List<RuleTemplateInputPoint> inputPoints;
  private final List<RuleTemplateInputConst> inputConsts;
  private final List<RuleTemplateOutputPoint> outputPoints;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(RuleTemplate ruleTemplate) {
    return new Builder(ruleTemplate);
  }

  private RuleTemplate(Builder builder) {
    this.id = builder.id;
    this.faultNumber = builder.faultNumber;
    this.name = builder.name;
    this.description = builder.description;
    this.nodeFilterExpression = builder.nodeFilterExpression;
    this.active = builder.active;
    this.templateTags = builder.templateTags;
    this.inputPoints = builder.inputPoints;
    this.inputConsts = builder.inputConsts;
    this.outputPoints = builder.outputPoints;
  }

  public int getId() {
    return id;
  }

  @JsonProperty("fault_number")
  public String getFaultNumber() {
    return faultNumber;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @JsonProperty("node_filter_expression")
  public String getNodeFilterExpression() {
    return nodeFilterExpression;
  }

  public boolean getActive() {
    return active;
  }

  @JsonProperty("template_tags")
  public List<Tag> getTemplateTags() {
    return templateTags;
  }

  @JsonProperty("input_points")
  public List<RuleTemplateInputPoint> getInputPoints() {
    return inputPoints;
  }

  @JsonProperty("input_consts")
  public List<RuleTemplateInputConst> getInputConsts() {
    return inputConsts;
  }

  @JsonProperty("output_points")
  public List<RuleTemplateOutputPoint> getOutputPoints() {
    return outputPoints;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private Integer id;
    private String faultNumber;
    private String name;
    private String description;
    private String nodeFilterExpression;
    private Boolean active;
    private List<Tag> templateTags;
    private List<RuleTemplateInputPoint> inputPoints;
    private List<RuleTemplateInputConst> inputConsts;
    private List<RuleTemplateOutputPoint> outputPoints;

    private Builder() {}

    private Builder(RuleTemplate ruleTemplate) {
      requireNonNull(ruleTemplate, "ruleTemplate cannot be null");
      this.id = ruleTemplate.id;
      this.faultNumber = ruleTemplate.faultNumber;
      this.name = ruleTemplate.name;
      this.description = ruleTemplate.description;
      this.nodeFilterExpression = ruleTemplate.nodeFilterExpression;
      this.active = ruleTemplate.active;
      this.templateTags = ruleTemplate.templateTags;
      this.inputPoints = ruleTemplate.inputPoints;
      this.inputConsts = ruleTemplate.inputConsts;
      this.outputPoints = ruleTemplate.outputPoints;
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

    @JsonProperty("fault_number")
    public Builder withFaultNumber(String faultNumber) {
      this.faultNumber = faultNumber;
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

    @JsonProperty("node_filter_expression")
    public Builder withNodeFilterExpression(String nodeFilterExpression) {
      this.nodeFilterExpression = nodeFilterExpression;
      return this;
    }

    public Builder withActive(boolean active) {
      this.active = active;
      return this;
    }

    @JsonProperty("template_tags")
    public Builder withTemplateTags(List<Tag> templateTags) {
      if (templateTags == null || templateTags.size() == 1 && templateTags.get(0) == null) {
        this.templateTags = ImmutableList.of();
      } else {
        this.templateTags = ImmutableList.copyOf(templateTags);
      }
      return this;
    }

    @JsonProperty("input_points")
    public Builder withInputPoints(List<RuleTemplateInputPoint> inputPoints) {
      requireNonNull(inputPoints, "inputPoints cannot be null");
      if (inputPoints.size() == 1 && inputPoints.get(0) == null) {
        this.inputPoints = ImmutableList.of();
      } else {
        this.inputPoints = ImmutableList.copyOf(inputPoints);
      }
      return this;
    }

    @JsonProperty("input_consts")
    public Builder withInputConsts(List<RuleTemplateInputConst> inputConsts) {
      requireNonNull(inputConsts, "inputConsts cannot be null");
      if (inputConsts.size() == 1 && inputConsts.get(0) == null) {
        this.inputConsts = ImmutableList.of();
      } else {
        this.inputConsts = ImmutableList.copyOf(inputConsts);
      }
      return this;
    }

    @JsonProperty("output_points")
    public Builder withOutputPoints(List<RuleTemplateOutputPoint> outputPoints) {
      requireNonNull(outputPoints, "outputPoints cannot be null");
      if (outputPoints.size() == 1 && outputPoints.get(0) == null) {
        this.outputPoints = ImmutableList.of();
      } else {
        this.outputPoints = ImmutableList.copyOf(outputPoints);
      }
      return this;
    }

    public RuleTemplate build() {
      requireNonNull(id, "id cannot be null");
      requireNonNull(name, "name cannot be null");
      requireNonNull(description, "description cannot be null");
      requireNonNull(active, "active cannot be null");
      requireNonNull(templateTags, "templateTags cannot be null");
      requireNonNull(inputPoints, "inputPoints cannot be null");
      requireNonNull(inputConsts, "inputConsts cannot be null");
      requireNonNull(outputPoints, "outputPoints cannot be null");
      return new RuleTemplate(this);
    }
  }
}
