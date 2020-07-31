package stream;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Peek {

  public static void main(String[] args) {
    List<Integer> numbers = Arrays.asList(2, 3, 4, 5, 6);

    List<Integer> result = numbers.stream()
        .peek(x -> System.out.println("from stream: " + x))
        .map(x -> x + 17)
        .peek(x -> System.out.println("after map: " + x))
        .filter(x -> x % 2 == 0)
        .peek(x -> System.out.println("after filter: " + x))
        .limit(2)
        .peek(x -> System.out.println("after limit: " + x))
        .collect(toList());

    System.out.println("Result: " + result);
    System.out.println();

    int roll = IntStream.range(0, 4)
        .map(i -> Die.D6.roll())
        .sorted()
        .peek(r -> System.out.print(r + " "))
        .skip(1)
        .sum();

    System.out.println();
    System.out.println("Roll: " + roll);

  }

}
