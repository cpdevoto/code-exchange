package heightweight;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public enum Die implements NumberGenerator {
  D4(4), D6(6), D8(8), D10(10), D12(12), D20(20), D100(100);

  private static final Map<Integer, Die> DICE = ImmutableMap.copyOf(Arrays.stream(Die.values())
      .collect(toMap(Die::getType, identity())));

  private final int type;

  public static Die get(int type) {
    return DICE.get(type);
  }

  private Die(int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }

  public double average() {
    return (1.0 + type) / 2;
  }

  public int roll() {
    return (int) (Math.random() * type + 1);
  }

  @Override
  public int min() {
    return 1;
  }

  @Override
  public int max() {
    return type;
  }
}
