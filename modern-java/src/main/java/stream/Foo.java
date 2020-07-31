package stream;

import static java.util.Comparator.reverseOrder;

import java.util.stream.Stream;

// Adding stream() and parallelStream() methods to an arbitrary class
public class Foo<T> {
  private final T t1;
  private final T t2;
  private final T t3;

  public Foo(T t1, T t2, T t3) {
    this.t1 = t1;
    this.t2 = t2;
    this.t3 = t3;
  }

  public Stream<T> stream() {
    return Stream.of(t1, t2, t3);
  }

  public Stream<T> parallelStream() {
    return Stream.of(t1, t2, t3).parallel();
  }

  public static void main(String[] args) {
    Foo<String> words = new Foo<>("animal", "mineral", "vegatable");

    words.parallelStream()
        .sorted(reverseOrder())
        .limit(1)
        .forEach(System.out::println);
  }

}
