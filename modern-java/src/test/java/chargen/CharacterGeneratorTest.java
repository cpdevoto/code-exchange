package chargen;

import static chargen.CharacterGenerator.DEVO;
import static chargen.CharacterGenerator.MATT_COLVILLE;
import static chargen.CharacterGenerator.STANDARD;
import static chargen.CharacterGenerator.WEBDM;
import static java.util.Comparator.reverseOrder;

import java.util.IntSummaryStatistics;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.junit.Test;

public class CharacterGeneratorTest {

  @Test
  public void test_stats() {
    generateStats(STANDARD);
    generateStats(WEBDM);
    generateStats(MATT_COLVILLE);
    generateStats(DEVO);
  }

  @Test
  public void test_point_buy_stats() {
    generatePointBuyStats(STANDARD);
    generatePointBuyStats(WEBDM);
    generatePointBuyStats(MATT_COLVILLE);
    generatePointBuyStats(DEVO);
  }

  @Test
  public void test_generate_10() {
    generate(STANDARD, 10);
    generate(WEBDM, 10);
    generate(MATT_COLVILLE, 10);
    generate(DEVO, 10);
  }

  @Test
  public void test_generate_1() {
    generate(STANDARD, 1);
    generate(WEBDM, 1);
    generate(MATT_COLVILLE, 1);
    generate(DEVO, 1);
  }

  @Test
  public void test_generate_standard_3() {
    generate(STANDARD, 3);
  }

  private void generate(CharacterGenerator generator, int num) {
    System.out.println("Generating " + num + " character(s) with " + generator + ":");
    IntStream.range(0, num)
        .mapToObj(i -> generator.generateAttributes())
        .map(list -> {
          list.sort(reverseOrder());
          return list;
        })
        .forEach(System.out::println);
    System.out.println();
  }

  private void generateStats(CharacterGenerator generator) {
    System.out.println("Generating summary statistics for " + generator + " stategy...");
    System.out.println(generator + " Results: " + timed(() -> getSummaryStats(generator)) + "\n");
  }

  private void generatePointBuyStats(CharacterGenerator generator) {
    System.out.println("Generating point buy statistics for " + generator + " stategy...");
    System.out.println(generator + " Results: " + timed(() -> getPointBuyStats(generator)) + "\n");
  }

  private IntSummaryStatistics timed(Supplier<IntSummaryStatistics> supplier) {
    long start = System.nanoTime();
    IntSummaryStatistics stats = supplier.get();
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println("Elapsed Time: " + duration + "ms");
    return stats;
  }

  private IntSummaryStatistics getSummaryStats(CharacterGenerator generator) {
    int iterations = 10_000_000;
    IntSummaryStatistics stats = IntStream.range(0, iterations)
        // .unordered()
        // .parallel() // This actually makes it slower!
        .flatMap(i -> generator.generateAttributes()
            .stream().mapToInt(Integer::intValue))
        .summaryStatistics();
    return stats;
  }

  private IntSummaryStatistics getPointBuyStats(CharacterGenerator generator) {
    int iterations = 10_000_000;
    IntSummaryStatistics stats = IntStream.range(0, iterations)
        .map(i -> generator.generateAttributes().stream()
            .mapToInt(attr -> pointBuyCost(attr))
            .sum())
        .summaryStatistics();
    return stats;
  }

  public int pointBuyCost(int attribute) {
    if (attribute < 14) {
      return attribute - 8;
    } else {
      return 5 + 2 * (attribute - 13);
    }
  }
}
