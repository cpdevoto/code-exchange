package stream;

public enum Die {
  // @formatter:off
  D4(4),
  D6(6),
  D8(8),
  D10(10),
  D12(12),
  D20(20),
  D100(100);
  // @formatter:on

  private final int type;

  private Die(int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }

  public int roll() {
    return (int) (Math.random() * type) + 1;
  }

}
