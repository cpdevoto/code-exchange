package heightweight;

public class Modifier implements NumberGenerator {

  private final int value;

  public Modifier(int value) {
    this.value = value;
  }

  @Override
  public double average() {
    return value;
  }

  @Override
  public int min() {
    return value;
  }

  @Override
  public int max() {
    return value;
  }

  @Override
  public int roll() {
    return value;
  }

}
