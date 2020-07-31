package collections;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Main {
  private static Map<String, List<String>> teamPlayers = Maps.newHashMap();
  private static Map<String, Integer> favoriteMovieCounts = Maps.newHashMap();

  private static void addTeamPlayer(String team, String player) {
    teamPlayers.computeIfAbsent(team, (t) -> Lists.newArrayList()).add(player);
  }

  private static void setFavoriteMovie(String movie) {
    favoriteMovieCounts.merge(movie, 1, Integer::sum);
  }

  public static void main(String[] args) {
    addTeamPlayer("Cowboys", "Roger Staubach");
    addTeamPlayer("Cowboys", "Tony Dorsett");
    addTeamPlayer("Steelers", "Terry Bradshaw");
    addTeamPlayer("Steelers", "Franco Harris");

    teamPlayers.forEach((team, players) -> System.out
        .println(team + ": " + players.stream().collect(joining(", "))));

    Map<Integer, String> map = Maps.newHashMap();
    map.put(1, "one");
    map.put(2, "two");
    map.replaceAll((key, value) -> value.toUpperCase());
    System.out.println(map);

    Set<String> set1 = Lists.newArrayList("one", "two", "three", "four")
        .stream().collect(toCollection(LinkedHashSet::new));
    Set<String> set2 = Lists.newArrayList("two", "four")
        .stream().collect(toCollection(LinkedHashSet::new));

    set1.removeIf(s -> set2.contains(s));

    System.out.println(set1);

    Map<String, String> family = ImmutableMap.of("Teo", "Star Wars", "Christina", "James Bond");
    Map<String, String> friends = ImmutableMap.of("Ralph", "Star Wars", "Christina", "Matrix");

    Map<String, String> everyone = Maps.newHashMap(family);

    friends.forEach(
        (key, value) -> everyone.merge(key, value, (value1, value2) -> value1 + " and " + value2));

    System.out.println(everyone);

    setFavoriteMovie("Matrix");
    setFavoriteMovie("Matrix");
    setFavoriteMovie("Jaws");

    System.out.println(favoriteMovieCounts);

    ConcurrentHashMap<String, Long> cmap = new ConcurrentHashMap<>();
    cmap.put("Matrix", 2L);
    cmap.put("Jaws", 1L);

    long parallelismThreshold = 1;
    Optional<Long> maxValue =
        Optional.ofNullable(cmap.reduceValues(parallelismThreshold, Long::max));

    System.out.println(maxValue);

  }

}
