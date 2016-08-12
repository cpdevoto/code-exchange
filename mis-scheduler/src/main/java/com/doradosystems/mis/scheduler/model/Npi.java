package com.doradosystems.mis.scheduler.model;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Npi.Builder.class)
public class Npi {
  private final long id;
  private final long operatorId;
  private final String regionCode;
  
  @JsonCreator
  @Nonnull
  public static Builder builder() {
    return new Builder();
  }

  private Npi(@Nonnull Builder builder) {
    this.id = builder.id;
    this.operatorId = builder.operatorId;
    this.regionCode = builder.regionCode;
  }
  
  public long getId() {
    return id;
  }
  
  public long getOperatorId() {
    return operatorId;
  }

  @Nonnull
  public String getRegionCode() {
    return regionCode;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + (int) (operatorId ^ (operatorId >>> 32));
    result = prime * result + ((regionCode == null) ? 0 : regionCode.hashCode());
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
    Npi other = (Npi) obj;
    if (id != other.id)
      return false;
    if (operatorId != other.operatorId)
      return false;
    if (regionCode == null) {
      if (other.regionCode != null)
        return false;
    } else if (!regionCode.equals(other.regionCode))
      return false;
    return true;
  }

  @JsonPOJOBuilder
  public static class Builder {
    private Long id;
    private Long operatorId;
    private String regionCode;
    
    private Builder() {}

    public Builder withId(long id) {
      checkArgument(id >= 0, "id must be a non-negative number"); 
      this.id = id;
      return this;
    }

    public Builder withOperatorId(long operatorId) {
      checkArgument(operatorId >= 0, "operatorId must be a non-negative number"); 
      this.operatorId = operatorId;
      return this;
    }

    public Builder withRegionCode(@Nonnull String regionCode) {
      requireNonNull(regionCode, "password cannot be null");
      this.regionCode = regionCode;
      return this;
    }
    
    public Npi build () {
      requireNonNull(id, "id cannot be null");
      requireNonNull(operatorId, "operatorId cannot be null");
      requireNonNull(regionCode, "password cannot be null");
      return new Npi(this);      
    }
  }

}
