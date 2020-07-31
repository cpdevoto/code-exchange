package collectors;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import collectors.Dish.Type;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupingMain {

  public enum CaloricLevel {
    DIET, NORMAL, FAT
  }


  public static void main(String[] args) {
    // <<groupingBy with filtering>>
    // TASK: Group dishes by type filtering out those dishes that have less than or equal to 500
    // calories

    // FIRST ATTEMPT: filter out low calorie dishes BEFORE grouping.
    // PROBLEM: Since none of the FISH dishes have 500 or more calories,
    // the resulting map is missing a FISH entry. Ideally we want an empty FISH entry!
    Map<Dish.Type, List<Dish>> caloricDishesByType = Dish.MENU.stream()
        .filter(dish -> dish.getCalories() > 500)
        .collect(groupingBy(Dish::getType));

    System.out.println(caloricDishesByType);

    // SECOND ATTEMPT: filter out low calorie dishes AFTER grouping.
    // PROBLEM: Since none of the FISH dishes have 500 or more calories,
    // the resulting map is missing a FISH entry; ideally we want an empty fish entry!

    // The following code does
    caloricDishesByType = Dish.MENU.stream()
        .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));

    System.out.println(caloricDishesByType);

    // <<groupingBy with mapping>>
    // TASK: Group dishes by type but the resulting lists should be List<String>s with the dish
    // names
    // instead of List<Dish>s
    Map<Dish.Type, List<String>> dishNamesByType = Dish.MENU.stream()
        .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));

    System.out.println(dishNamesByType);

    // <<groupingBy with flatMapping>>
    // TASK: Given a set of tags for each dish, compute the tags for each dish type, eliminating all
    // duplicates!

    Map<String, List<String>> dishTags = new HashMap<>();
    dishTags.put("pork", asList("greasy", "salty"));
    dishTags.put("beef", asList("salty", "roasted"));
    dishTags.put("chicken", asList("fried", "crisp"));
    dishTags.put("french fries", asList("greasy", "fried"));
    dishTags.put("rice", asList("light", "natural"));
    dishTags.put("season fruit", asList("fresh", "natural"));
    dishTags.put("pizza", asList("tasty", "salty"));
    dishTags.put("prawns", asList("tasty", "roasted"));
    dishTags.put("salmon", asList("delicious", "fresh"));

    Map<Dish.Type, Set<String>> dishTagsByType = Dish.MENU.stream()
        .collect(groupingBy(Dish::getType,
            flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));

    System.out.println(dishTagsByType);

    // <<multi-level grouping>>
    // TASK: Group dished by dish type and caloric level

    Map<Dish.Type, Map<CaloricLevel, List<String>>> dishesByTypeAndCaloricLevel = Dish.MENU.stream()
        .collect(groupingBy(
            Dish::getType,
            groupingBy(dish -> {
              if (dish.getCalories() <= 400)
                return CaloricLevel.DIET;
              else if (dish.getCalories() <= 700)
                return CaloricLevel.NORMAL;
              else
                return CaloricLevel.FAT;
            }, mapping(Dish::getName, toSortedList(naturalOrder())))));

    System.out.println(dishesByTypeAndCaloricLevel);

    // <<groupingBy with counting>>
    // TASK: Group dished by dish type and caloric level

    Map<Dish.Type, Long> typeCounts = Dish.MENU.stream()
        .collect(groupingBy(
            Dish::getType,
            Collectors.counting()));

    System.out.println(typeCounts);

    // <<groupingBy with maxBy, also using collectingAndThen to transform Optional<Dish> into
    // String>>
    // TASK: Find the highest calorie dish of each type
    Map<Dish.Type, String> mostCaloricByType = Dish.MENU.stream()
        .collect(groupingBy(
            Dish::getType,
            collectingAndThen(
                maxBy(comparingInt(Dish::getCalories)),
                max -> max.get().getName())));

    System.out.println(mostCaloricByType);

    // <<groupingBy with summingInt>>
    // Get the total calories by type
    Map<Dish.Type, Integer> totalCaloriesByType = Dish.MENU.stream()
        .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));

    System.out.println(totalCaloriesByType);

    // <<partioningBy>>
    // TASK: Get lists of all vegetarian and non-vegetarian dishes; can achieve similar result with
    // intermediate filter operation, but here, you get both sets of results. Can also use for
    // multi-level groupings
    Map<Boolean, List<String>> menuPartitionedByIsVegetarian = Dish.MENU.stream()
        .collect(partitioningBy(Dish::isVegetarian,
            mapping(Dish::getName, toSortedList(naturalOrder()))));

    System.out.println(menuPartitionedByIsVegetarian.get(true));
    System.out.println(menuPartitionedByIsVegetarian.get(false));

    // <<partioningBy with groupingBy>>
    // Group vegetarian and non-vegetarian dishes by type
    Map<Boolean, Map<Dish.Type, List<String>>> dishesByIsVegetarianAndType = Dish.MENU.stream()
        .collect(partitioningBy(
            Dish::isVegetarian,
            groupingBy(Dish::getType, mapping(Dish::getName, toSortedList(naturalOrder())))));

    System.out.println(dishesByIsVegetarianAndType);
  }

  static <T> Collector<T, ?, List<T>> toSortedList(Comparator<? super T> c) {
    return Collectors.collectingAndThen(
        toCollection(ArrayList::new), l -> {
          l.sort(c);
          return l;
        });
  }

  // Adapted from Java 9!
  public static <T, A, R> Collector<T, ?, R> filtering(Predicate<? super T> predicate,
      Collector<? super T, A, R> downstream) {
    BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();

    Characteristics[] characteristics = downstreamCharacteristics(downstream);

    return Collector.of(downstream.supplier(),
        (r, t) -> {
          if (predicate.test(t)) {
            downstreamAccumulator.accept(r, t);
          }
        },
        downstream.combiner(), downstream.finisher(),
        characteristics);
  }


  // Adapted from Java 9!
  public static <T, U, A, R> Collector<T, ?, R> flatMapping(
      Function<? super T, ? extends Stream<? extends U>> mapper,
      Collector<? super U, A, R> downstream) {
    BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
    return Collector.of(downstream.supplier(),
        (r, t) -> {
          try (Stream<? extends U> result = mapper.apply(t)) {
            if (result != null)
              result.sequential().forEach(u -> downstreamAccumulator.accept(r, u));
          }
        },
        downstream.combiner(), downstream.finisher(),
        downstreamCharacteristics(downstream));
  }


  private static <T, A, R> Characteristics[] downstreamCharacteristics(
      Collector<? super T, A, R> downstream) {
    Characteristics[] characteristics = new Characteristics[downstream.characteristics().size()];
    characteristics = downstream.characteristics().toArray(characteristics);
    return characteristics;
  }
}
