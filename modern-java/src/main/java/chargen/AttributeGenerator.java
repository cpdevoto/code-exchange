package chargen;

import java.util.stream.IntStream;

public enum AttributeGenerator {
  ROLL_3d6 {

    @Override
    public int generateAttribute() {
      return generateAttribute(3);
    }

  },
  ROLL_4d6_DROP_LOWEST {

    @Override
    public int generateAttribute() {
      return generateAttribute(4);
    }

  },
  ROLL_4d6_DROP_LOWEST_DISCARD_LESS_THAN_8 {

    @Override
    public int generateAttribute() {
      int attribute;
      do {
        attribute = generateAttribute(4);
      } while (attribute < 8);
      return attribute;
    }

  };

  public abstract int generateAttribute();

  private static int generateAttribute(int numDice) {
    IntStream stream = IntStream.range(0, numDice)
        .map(i -> Die.D6.roll());

    if (numDice > 3) {
      stream = stream.sorted()
          .skip(numDice - 3);
    }

    return stream.sum();
  }

}
