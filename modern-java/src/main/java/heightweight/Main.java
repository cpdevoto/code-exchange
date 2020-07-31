package heightweight;

import static java.util.Comparator.comparing;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) {

    Arrays.stream(Race.values()).forEach(Race::printHeightWeightStats);


    System.out.println("SHORTEST RACE:");
    System.out.println();
    Arrays.stream(Race.values())
        .sorted(comparing(Race::getMinHeight).thenComparing(Race::getName))
        .findFirst()
        .ifPresent(Race::printHeightWeightStats);

    System.out.println("TALLEST RACE:");
    System.out.println();
    Arrays.stream(Race.values())
        .sorted(comparing(Race::getMaxHeight).reversed().thenComparing(Race::getName))
        .findFirst()
        .ifPresent(Race::printHeightWeightStats);

    System.out.println("LIGHTEST RACE:");
    System.out.println();
    Arrays.stream(Race.values())
        .sorted(comparing(Race::getMinWeight).thenComparing(Race::getName))
        .findFirst()
        .ifPresent(Race::printHeightWeightStats);

    System.out.println("HEAVIEST RACE:");
    System.out.println();
    Arrays.stream(Race.values())
        .sorted(comparing(Race::getMaxWeight).reversed().thenComparing(Race::getName))
        .findFirst()
        .ifPresent(Race::printHeightWeightStats);


    System.out.println("MY CURRENT RACE:");
    System.out.println();
    Race.GNOME.printHeightWeightStats();
  }

}
