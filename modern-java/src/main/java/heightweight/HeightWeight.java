package heightweight;

import static java.util.Objects.requireNonNull;

public class HeightWeight {
  private final Height height;
  private final Weight weight;

  public HeightWeight(Height height, Weight weight) {
    this.height = requireNonNull(height, "height cannot be null");
    this.weight = requireNonNull(weight, "weight cannot be null");
  }

  public Height getHeight() {
    return height;
  }

  public Weight getWeight() {
    return weight;
  }

}
