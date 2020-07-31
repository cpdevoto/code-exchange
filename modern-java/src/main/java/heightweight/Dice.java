package heightweight;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public class Dice implements NumberGenerator {

  private final int numDice;
  private final Die die;

  public Dice(int numDice, Die die) {
    checkArgument(numDice > 0, "numDice must be greater than zero");
    this.numDice = numDice;
    this.die = requireNonNull(die, "die cannot be null");
  }

  @Override
  public double average() {
    return numDice * die.average();
  }

  @Override
  public int roll() {
    int total = 0;
    for (int i = 0; i < numDice; i++) {
      total += die.roll();
    }
    return total;
  }

  @Override
  public int min() {
    return numDice * die.min();
  }

  @Override
  public int max() {
    return numDice * die.max();
  }


}
