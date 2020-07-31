package collectors;

import static java.util.stream.Collectors.partitioningBy;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Primes {

  public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
    return IntStream.rangeClosed(2, n)
        .boxed()
        .collect(new PrimeNumberCollector());
  }

  private static boolean isPrime(int candidate) {
    int candidateRoot = (int) Math.sqrt((double) candidate);
    return IntStream.rangeClosed(2, candidateRoot)
        .noneMatch(i -> candidate % i == 0);
  }

  private static Map<Boolean, List<Integer>> partitionPrimesSimple(int n) {
    return IntStream.rangeClosed(2, n)
        .boxed()
        .collect(partitioningBy(Primes::isPrime));
  }

  public static void main(String[] args) {
    // Print all primes from 2 to 100
    System.out.println(partitionPrimesSimple(100).get(true));
  }
}
