package heightweight;

public class HeightWeightStats {

  public static void stats(HeightWeightGenerator generator) {
    printStat("AVERAGE", generator.average());
    printStat("MINUMUM", generator.min());
    printStat("MAXIMUM", generator.max());
    printStat("RANDOM", generator.roll());

  }

  private static void printStat(String stat, HeightWeight heightWeight) {
    System.out.println("\t" + stat + ":");
    System.out.println("\t\tHeight: " + heightWeight.getHeight());
    System.out.println("\t\tWeight: " + heightWeight.getWeight());
    System.out.println();
  }
}
