package stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {


  public static void main(String[] args) {
    // Get the list of all unique characters contained in the list of words (case-sensitive);
    String[] words = {"Hello", "World"};
    List<String> uniqueChars = Arrays.stream(words)
        .map(word -> word.split(""))
        .flatMap(Arrays::stream)
        .distinct()
        .collect(toList());

    System.out.println(uniqueChars);

    // Compute the squares of all numbers in a list
    List<Integer> squares = Stream.of(1, 2, 3, 4, 5)
        .map(n -> n * n)
        .collect(toList());

    System.out.println(squares);

    // Given two lists of numbers, return all pairs of numbers, keeping only those whose sum is
    // divisible by 3
    List<Integer> numbers1 = Arrays.asList(1, 2, 3);
    List<Integer> numbers2 = Arrays.asList(3, 4);

    List<String> pairs = numbers1.stream()
        .flatMap(i -> numbers2.stream()
            .filter(j -> (i + j) % 3 == 0)
            .map(j -> new int[] {i, j}))
        .map(Arrays::toString)
        .collect(toList());

    System.out.println(pairs);

    // Print a message only if a list of numbers contains an even number
    if (Stream.of(1, 2, 3, 5, 7).anyMatch(n -> n % 2 == 0)) {
      System.out.println("This list contains an even number");
    } else {
      System.out.println("This list contains no even numbers");
    }

    // Print a message only if a list of numbers contains all odd number
    if (Stream.of(1, 3, 5, 7).allMatch(n -> n % 2 == 1)) {
      System.out.println("This list contains all odd numbers");
    } else {
      System.out.println("This list contains an even number");
    }

    if (Stream.of(1, 3, 5, 7).noneMatch(n -> n % 2 == 0)) {
      System.out.println("This list contains all odd numbers");
    } else {
      System.out.println("This list contains an even number");
    }

    // Find any even number in a list of numbers
    // (findAny can return different results than findFirst with a parallel stream)
    Stream.of(1, 2, 3, 4, 5, 6, 7)
        .parallel()
        .filter(n -> n % 2 == 0)
        .findAny()
        .ifPresent(System.out::println);


    // Find the first even number in a list of numbers
    Stream.of(1, 2, 3, 4, 5, 6, 7)
        .parallel()
        .filter(n -> n % 2 == 0)
        .findFirst()
        .ifPresent(System.out::println);

    // REDUCE!!!!!
    // Using reduce to combine elements of a stream into a single value with having to mutate
    // external state in a non-threadsafe way

    // The non-functional way (not thread-safe; uses mutable accumulator anti-pattern)
    int sum = 0;
    for (int n : Arrays.asList(1, 2, 3, 4, 5)) {
      sum += n;
    }
    System.out.println("SUM: " + sum);

    // The functional way (thread-safe)
    sum = Arrays.asList(1, 2, 3, 4, 5).stream()
        .parallel()
        .reduce(0, Integer::sum); // Integer::sum has type sig of (n1, n2) -> n1 + n2

    System.out.println("SUM: " + sum);

    // Better yet from a readability perspective would be:
    sum = IntStream.of(1, 2, 3, 4, 5)
        .parallel()
        .sum();

    System.out.println("SUM: " + sum);

    // Count number of objects in stream (threadsafe)
    // NOTE: You could also just use Arrays.asList(1, 2).stream().count()
    long count = Arrays.asList(1, 2, 3, 4, 5).stream()
        .parallel()
        .map(i -> 1L) // convert each number in the stream to a 1
        .reduce(0L, Long::sum); // Long::sum has type sig of (n1, n2) -> n1 + n2

    System.out.println("COUNT: " + count);

    // Better yet from a readability perspective would be:
    count = IntStream.of(1, 2, 3, 4, 5)
        .parallel()
        .count();

    System.out.println("COUNT: " + count);

    // Generate 7 Attributes using 4d6 and drop lowest (discard if less than 8), and keep only the
    // top 6; if at least one attribute is not 15 or greater, reroll them all.

    List<Integer> attributes;
    do {
      attributes = IntStream.range(0, 7)
          .map(i -> {
            int roll;
            do {
              roll = IntStream.range(0, 4)
                  .map(j -> Die.D6.roll())
                  .sorted()
                  .skip(1)
                  .sum();
            } while (roll < 8);
            return roll;
          })
          .sorted()
          .skip(1)
          .boxed()
          // Use collectingAndThen to perform a final transformation on the result
          .collect(collectingAndThen(toList(), Collections::unmodifiableList));

    } while (attributes.stream()
        .mapToInt(attr -> attr)
        .max().getAsInt() < 15);

    System.out.println(attributes.stream()
        .map(String::valueOf)
        .collect(joining(", ", "Attributes: ", "")));

    // Stream.iterate(T seed, UnaryOperator<T> f) --> generate unbounded stream
    // Generate first 20 numbers of Fibonacci series using Stream.iterate
    // Generate an unbounded stream of fibonacci tuples -> (0, 1), (1, 2), (2, 3), (3, 5)
    // then map each tuple to a single number by extracting the first element
    String series = Stream
        .iterate(new int[] {0, 1}, fibTuple -> new int[] {fibTuple[1], fibTuple[0] + fibTuple[1]})
        .parallel()
        .limit(20)
        .map(fibTuple -> fibTuple[0])
        .map(String::valueOf)
        .collect(Collectors.joining(", ", "(", ")"));

    System.out.println("Fibonacci: " + series);

    // Stream.generate(Supplier<T> s) --> roll 4d6 drop lowest
    int roll = IntStream.generate(Die.D6::roll)
        .parallel()
        .limit(4)
        .sorted()
        .skip(1)
        .sum();
    System.out.println("Roll: " + roll);


  }
}
