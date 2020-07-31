package heightweight;

import static com.google.common.base.Preconditions.checkArgument;

public final class Weight implements Comparable<Weight> {

  private final int pounds;

  public Weight(int pounds) {
    checkArgument(pounds >= 0, "pounds cannot be less than zero");
    this.pounds = pounds;
  }

  public int toPounds() {
    return pounds;
  }

  @Override
  public String toString() {
    return pounds + " lbs.";
  }

  @Override
  public int compareTo(Weight o) {
    return pounds - o.pounds;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + pounds;
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
    Weight other = (Weight) obj;
    if (pounds != other.pounds)
      return false;
    return true;
  }



}
