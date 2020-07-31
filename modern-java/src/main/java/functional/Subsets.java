package functional;

import static java.util.stream.Collectors.joining;

import java.util.Comparator;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Subsets {

  private static final Comparator<List<Integer>> subsetComparator = (o1, o2) -> {
    int c = Integer.compare(o1.size(), o2.size());
    if (c == 0) {
      // The two lists are the same size, so do element-wise comparison
      for (int i = 0; i < o1.size(); i++) {
        c = o1.get(i).compareTo(o2.get(i));
        if (c != 0) {
          break;
        }
      }
    }
    return c;
  };


  public static <T> List<List<T>> subsets(List<T> list) {
    if (list.isEmpty()) {
      return ImmutableList.of(ImmutableList.of());
    }

    T first = list.get(0);
    List<T> rest = list.subList(1, list.size());
    List<List<T>> subAnswer1 = subsets(rest);
    List<List<T>> subAnswer2 = insertAll(first, subAnswer1);
    return concat(subAnswer1, subAnswer2);

  }

  private static <T> List<List<T>> insertAll(T first, List<List<T>> lists) {
    List<List<T>> result = Lists.newArrayList();
    for (List<T> list : lists) {
      List<T> subResult = Lists.newArrayList();
      subResult.add(first);
      subResult.addAll(list);
      result.add(ImmutableList.copyOf(subResult));
    }
    return ImmutableList.copyOf(result);
  }

  private static <T> List<List<T>> concat(List<List<T>> list1,
      List<List<T>> list2) {
    List<List<T>> result = Lists.newArrayList();
    result.addAll(list1);
    result.addAll(list2);
    return ImmutableList.copyOf(result);
  }

  public static void main(String[] args) {
    List<Integer> list = ImmutableList.of(1, 4, 9);
    System.out.println("The possible subsets of " + toSetString(list) + " are: " +
        subsets(list).stream()
            .sorted(subsetComparator)
            .map(Subsets::toSetString)
            .collect(joining(", ", "{", "}")));
  }

  private static <T> String toSetString(List<T> subset) {
    return subset.stream()
        .sorted()
        .map(String::valueOf)
        .collect(joining(",", "{", "}"));
  }

}
