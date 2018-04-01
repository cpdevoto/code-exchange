package com.shrinedev.avs.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableList;

import static java.util.Objects.requireNonNull;

@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = ArtifactVersions.Builder.class)
public class ArtifactVersions {
  private final String name;
  private final List<Version> tags;

  @JsonCreator
  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(ArtifactVersions artifactVersions) {
    return new Builder(artifactVersions);
  }

  private ArtifactVersions(Builder builder) {
    this.name = builder.name;
    this.tags = builder.tags;
  }

  public String getName() {
    return name;
  }

  public List<String> getTags() {
    return tags.stream().map(Version::toString).collect(Collectors.toList());
  }

  public Optional<Version> latest() {
    if (tags.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(tags.get(0));
  }

  @JsonPOJOBuilder
  public static class Builder {
    private String name;
    private List<Version> tags;

    private Builder() {}

    private Builder(ArtifactVersions artifactVersions) {
      requireNonNull(artifactVersions, "artifactVersions cannot be null");
      this.name = artifactVersions.name;
      this.tags = artifactVersions.tags;
    }

    public Builder withName(String name) {
      requireNonNull(name, "name cannot be null");
      this.name = name;
      return this;
    }

    public Builder withTags(List<String> tags) {
      requireNonNull(tags, "tags cannot be null");
      List<Version> versions =
          tags.stream().map(tag -> new Version(tag)).collect(Collectors.toList());
      Collections.sort(versions);
      this.tags = ImmutableList.copyOf(versions);
      return this;
    }

    public ArtifactVersions build() {
      requireNonNull(name, "name cannot be null");
      requireNonNull(tags, "tags cannot be null");
      return new ArtifactVersions(this);
    }
  }
}
