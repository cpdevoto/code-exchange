package heightweight;

import static java.util.Objects.requireNonNull;

public class HeightWeightGenerator {
  private final Height baseHeight;
  private final Weight baseWeight;
  private final NumberGenerator sizeModifier;
  private final NumberGenerator weightMultiplier;

  public static Builder builder() {
    return new Builder();
  }

  private HeightWeightGenerator(Builder builder) {
    this.baseHeight = builder.baseHeight;
    this.baseWeight = builder.baseWeight;
    this.sizeModifier = builder.sizeModifier;
    this.weightMultiplier = builder.weightMultiplier;
  }

  public HeightWeight average() {
    double sizeMod = sizeModifier.average();
    int height = (int) Math.round(baseHeight.toInches() + sizeMod);
    int weight = (int) Math.round(baseWeight.toPounds() + (weightMultiplier.average() * sizeMod));

    return new HeightWeight(new Height(height), new Weight(weight));
  }

  public HeightWeight min() {
    int sizeMod = sizeModifier.min();
    int height = baseHeight.toInches() + sizeMod;
    int weight = baseWeight.toPounds() + (weightMultiplier.min() * sizeMod);

    return new HeightWeight(new Height(height), new Weight(weight));
  }

  public HeightWeight max() {
    int sizeMod = sizeModifier.max();
    int height = baseHeight.toInches() + sizeMod;
    int weight = baseWeight.toPounds() + (weightMultiplier.max() * sizeMod);

    return new HeightWeight(new Height(height), new Weight(weight));
  }


  public HeightWeight roll() {
    int sizeMod = sizeModifier.roll();
    int height = baseHeight.toInches() + sizeMod;
    int weight = baseWeight.toPounds() + (weightMultiplier.roll() * sizeMod);

    return new HeightWeight(new Height(height), new Weight(weight));
  }

  public static class Builder {
    private Height baseHeight;
    private Weight baseWeight;
    private NumberGenerator sizeModifier;
    private NumberGenerator weightMultiplier;

    private Builder() {}

    public Builder withBaseHeight(Height baseHeight) {
      this.baseHeight = requireNonNull(baseHeight, "baseHeight cannot be null");
      return this;
    }

    public Builder withBaseWeight(Weight baseWeight) {
      this.baseWeight = requireNonNull(baseWeight, "baseWeight cannot be null");
      return this;
    }

    public Builder withSizeModifier(NumberGenerator sizeModifier) {
      this.sizeModifier = requireNonNull(sizeModifier, "sizeModifier cannot be null");
      return this;
    }

    public Builder withWeightMultiplier(NumberGenerator weightMultiplier) {
      this.weightMultiplier = requireNonNull(weightMultiplier, "weightMultiplier cannot be null");
      return this;
    }

    public HeightWeightGenerator build() {
      requireNonNull(baseHeight, "baseHeight cannot be null");
      requireNonNull(baseWeight, "baseWeight cannot be null");
      requireNonNull(sizeModifier, "sizeModifier cannot be null");
      requireNonNull(weightMultiplier, "weightMultiplier cannot be null");
      return new HeightWeightGenerator(this);
    }

  }
}
