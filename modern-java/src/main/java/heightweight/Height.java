package heightweight;

import static com.google.common.base.Preconditions.checkArgument;

public final class Height implements Comparable<Height> {

  private final int inches;

  public Height(int feet, int inches) {
    checkArgument(feet >= 0, "feet cannot be less than zero");
    checkArgument(inches >= 0, "inches cannot be less than zero");
    this.inches = feet * 12 + inches;
  }

  public Height(int inches) {
    checkArgument(inches >= 0, "inches cannot be less than zero");
    this.inches = inches;
  }

  public int toInches() {
    return inches;
  }

  @Override
  public String toString() {
    return (inches / 12) + "' " + (inches % 12) + "\"";
  }

  @Override
  public int compareTo(Height o) {
    return inches - o.inches;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + inches;
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
    Height other = (Height) obj;
    if (inches != other.inches)
      return false;
    return true;
  }

}
