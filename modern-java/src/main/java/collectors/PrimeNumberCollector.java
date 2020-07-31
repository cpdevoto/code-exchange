package collectors;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PrimeNumberCollector
    implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

  private static <T> List<T> takeWhile(List<T> list, Predicate<T> p) {
    int i = 0;
    for (T item : list) {
      if (!p.test(item)) {
        return list.subList(0, i);
      }
      i++;
    }
    return list;
  }

  private static boolean isPrime(List<Integer> primes, int candidate) {
    int candidateRoot = (int) Math.sqrt((double) candidate);
    return takeWhile(primes, i -> i <= candidateRoot)
        .stream()
        .noneMatch(p -> candidate % p == 0);
  }

  @SuppressWarnings("serial")
  @Override
  public Supplier<Map<Boolean, List<Integer>>> supplier() {
    return () -> new HashMap<Boolean, List<Integer>>() {
      {
        put(true, new ArrayList<Integer>());
        put(false, new ArrayList<Integer>());
      }
    };
  }

  @Override
  public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
    return (acc, candidate) -> {
      acc.get(isPrime(acc.get(true), candidate))
          .add(candidate);
    };
  }

  @Override
  public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
    return (map1, map2) -> {
      map1.get(true).addAll(map2.get(true));
      map1.get(false).addAll(map2.get(false));
      return map1;
    };
  }

  @Override
  public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
    return Function.identity();
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
  }

  public static void main(String[] args) {
    System.out.println(Primes.partitionPrimes(100).get(true));
  }
}
