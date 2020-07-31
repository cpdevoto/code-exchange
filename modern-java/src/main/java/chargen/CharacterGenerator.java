package chargen;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.maxBy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CharacterGenerator {
  STANDARD {

    @Override
    public List<Integer> generateAttributes() {
      return generateAttributes(AttributeGenerator.ROLL_4d6_DROP_LOWEST, 6);
    }

  },
  WEBDM {
    @Override
    public List<Integer> generateAttributes() {
      return generateAttributes(AttributeGenerator.ROLL_4d6_DROP_LOWEST, 7);
    }
  },
  MATT_COLVILLE {
    @Override
    public List<Integer> generateAttributes() {
      return generateAttributes(AttributeGenerator.ROLL_4d6_DROP_LOWEST_DISCARD_LESS_THAN_8, 6);
    }
  },
  DEVO {
    @Override
    public List<Integer> generateAttributes() {
      List<Integer> attributes;
      do {
        attributes =
            generateAttributes(AttributeGenerator.ROLL_4d6_DROP_LOWEST_DISCARD_LESS_THAN_8, 6);
      } while (attributes.stream().collect(maxBy(naturalOrder())).get() < 15);
      return attributes;
    }
  };

  public abstract List<Integer> generateAttributes();

  private static List<Integer> generateAttributes(AttributeGenerator attributeGenerator,
      int numAttributes) {
    Stream<Integer> stream = Stream.generate(() -> attributeGenerator.generateAttribute())
        .limit(numAttributes);
    if (numAttributes > 6) {
      stream = stream.sorted()
          .skip(numAttributes - 6);
    }
    return stream.collect(Collectors.toList());
  }

}
