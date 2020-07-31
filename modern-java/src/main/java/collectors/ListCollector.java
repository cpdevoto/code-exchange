package collectors;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListCollector<T> implements Collector<T, List<T>, List<T>> {
  // Collector<T, A, R>
  // T = The collector processes a Stream<T>
  // A = The collector uses an accumulator of type A
  // R = The type of the object returned by the collect operation (often same as A, but not always)

  @Override
  // returns a factory method that generates an empty result container
  public Supplier<List<T>> supplier() {
    return ArrayList::new;
  }

  @Override
  // returns a consumer adds an element to the result container (the result container is first arg,
  // element is
  // second arg)
  public BiConsumer<List<T>, T> accumulator() {
    return List::add;
  }

  @Override
  // returns a function transforms the result container into an object of the type returned by the
  // collect operation
  public Function<List<T>, List<T>> finisher() {
    return Function.identity();
  }

  @Override
  // returns a function that merges two result containers into one
  public BinaryOperator<List<T>> combiner() {
    return (list1, list2) -> {
      list1.addAll(list2);
      return list1;
    };
  }

  @Override
  public Set<Characteristics> characteristics() {
    // UNORDERED: The result of the reduction isn't affected by the order in which the items in the
    // stream are traversed and combined; false, because if the stream is ordered, we want to
    // preserved that order in the result
    // CONCURRENT: The accumulator function can be called concurrently from multiple threads; false,
    // because the result container is not thread-safe
    // IDENTITY_FINISH: Indicates that the function returned by the finisher method is the identity
    // one, and its application can be omitted; true
    return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
  }

  public static void main(String[] args) {
    // Using the custom ListCollector class
    List<String> vegetarianDishes = Dish.MENU.stream()
        .filter(Dish::isVegetarian)
        .map(Dish::getName)
        .collect(new ListCollector<>());
    System.out.println(vegetarianDishes);


    // 1. Creating a custom collector without having to build a new class (IDENTITY_FINISH assumed)
    // PROBLEM hard to read
    vegetarianDishes = Dish.MENU.stream()
        .filter(Dish::isVegetarian)
        .map(Dish::getName)
        .collect(
            ArrayList::new,
            List::add,
            List::addAll);
    System.out.println(vegetarianDishes);

    // 2. Creating a custom collector that can be reused without having to build a new class
    vegetarianDishes = Dish.MENU.stream()
        .filter(Dish::isVegetarian)
        .map(Dish::getName)
        .collect(listCollector());
    System.out.println(vegetarianDishes);

  }

  private static <T> Collector<T, List<T>, List<T>> listCollector() {
    return Collector.of(
        ArrayList::new, // supplier
        List::add, // accumulator
        (list1, list2) -> { // combiner
          list1.addAll(list2);
          return list1;
        },
        Function.identity(), // finisher
        IDENTITY_FINISH); // characteristics
  }

}
