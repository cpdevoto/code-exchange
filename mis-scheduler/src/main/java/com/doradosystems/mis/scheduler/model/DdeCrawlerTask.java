package com.doradosystems.mis.scheduler.model;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@JsonDeserialize(builder = DdeCrawlerTask.Builder.class)
public class DdeCrawlerTask {
  private final Operator operator;
  private final List<Npi> npis;

  @JsonCreator
  @Nonnull
  public static Builder builder () {
    return new Builder();
  }
  
  private DdeCrawlerTask(@Nonnull Builder builder) {
    this.operator = builder.operator;
    this.npis = ImmutableList.copyOf(builder.npis);
  }
  
  @Nonnull
  public Operator getOperator() {
    return operator;
  }

  @Nonnull
  public List<Npi> getNpis() {
    return npis;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((npis == null) ? 0 : npis.hashCode());
    result = prime * result + ((operator == null) ? 0 : operator.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DdeCrawlerTask other = (DdeCrawlerTask) obj;
    if (npis == null) {
      if (other.npis != null)
        return false;
    } else if (!npis.equals(other.npis))
      return false;
    if (operator == null) {
      if (other.operator != null)
        return false;
    } else if (!operator.equals(other.operator))
      return false;
    return true;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private Operator operator;
    private final List<Npi> npis = Lists.newArrayList();
    
    private Builder () {}

    @Nonnull
    public Builder withOperator(@Nonnull Operator operator) {
      requireNonNull(operator, "operator cannot be null");
      this.operator = operator;
      return this;
    }
    
    @Nonnull
    public Builder withNpi(@Nonnull Npi npi) {
      requireNonNull(npi, "npi cannot be null");
      this.npis.add(npi);
      return this;
    }
    
    @Nonnull
    public Builder withNpis(@Nonnull Collection<Npi> npis) {
      requireNonNull(npis, "npis cannot be null");
      this.npis.addAll(npis);
      return this;
    }
    
    @Nonnull
    public DdeCrawlerTask build () {
      requireNonNull(operator, "operator cannot be null");
      checkArgument(npis.size() > 0, "npis cannot be empty");
      return new DdeCrawlerTask(this);
    }
  }
}
