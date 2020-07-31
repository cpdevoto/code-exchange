package conjure;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

public class ConjurationSpell {

  private static final Map<String, String> CR_MAP = ImmutableMap.<String, String>builder()
      .put("0", "1/4")
      .put("1/8", "1/4")
      .put("1/4", "1/4")
      .put("1/2", "1/2")
      .put("1", "1")
      .put("2", "2")
      .build();

  public static ConjurationSpell CONJURE_WOODLAND_BEINGS = create(
      "1/8: Boggle\n" +
          "1/4:  Blink Dog, Pixie, Sprite\n" +
          "1/2: Darkling, Satyr\n" +
          "1:   Dryad, Quickling\n" +
          "2:   Sea Hag, Meenlock, Darkling Elder");

  public static ConjurationSpell CONJURE_ANIMALS = create(
      "0: Frog, Sea Horse, Baboon, Badger, Bat, Cat, Crab, Deer, Eagle, Giant Fire Beetle, Goat, Hawk, Hyena, Jackal, Lizard, Octopus, Owl, Quipper, Rat, Raven, Scorpion, Spider, Vulture, Weasel\n"
          +
          "1/8: Blood Hawk, Camel, Flying Snake, Giant Crab, Giant Rat, Giant Weasel, Mastiff, Mule, Poisonous Snake, Pony, Stirge\n"
          +
          "1/4: Axe Beak, Boar, Constrictor Snake, Draft Horse, Elk, Giant Badger, Giant Bat, Giant Centipede, Giant Frog, Giant Lizard, Giant Owl, Giant Poisonous Snake, Giant Wolf Spider, Panther, Riding Horse, Wolf\n"
          +
          "1/2: Ape, Black Bear, Crocodile, Giant Goat, Giant Sea Horse, Giant Wasp, Reef Shark, Warhorse\n"
          +
          "1: Brown Bear, Dire Wolf, Giant Eagle, Giant Hyena, Giant Octopus, Giant Spider, Giant Toad, Giant Vulture, Lion, Tiger\n"
          +
          "2: Giant Boar, Giant Constrictor Snake, Giant Elk, Hunter Shark, Plesiosaurus, Polar Bear, Rhinoceros, Saber-toothed Tiger");

  private final Map<String, List<String>> options;
  private final Map<String, List<String>> crs;

  private ConjurationSpell(Map<String, List<String>> options) {
    this.options = options;
    this.crs = options.keySet().stream()
        .collect(groupingBy(cr -> CR_MAP.get(cr), LinkedHashMap::new, toList()));
  }

  private static ConjurationSpell create(String options) {
    Map<String, List<String>> optionMap = new LinkedHashMap<>();
    Stream.of(options.split("\n"))
        .forEach(row -> {
          String[] columns = row.split(":");
          String cr = columns[0];
          List<String> opt = Stream.of(columns[1].split(","))
              .collect(toList());
          optionMap.put(cr, opt);
        });

    return new ConjurationSpell(optionMap);
  }

  public Set<String> challengeRatings() {
    return crs.keySet();
  }

  public String generate(String cr) {
    requireNonNull(cr, "CR cannot be null");
    checkArgument(crs.containsKey(cr.trim()), "Invalid CR " + cr);

    List<String> randomCrs = crs.get(cr);
    int idx = (int) (Math.random() * randomCrs.size());
    String randomCr = randomCrs.get(idx);

    List<String> opt = options.get(randomCr);
    idx = (int) (Math.random() * opt.size());
    return opt.get(idx);
  }


  public static void main(String[] args) {
    ConjurationSpell spell = ConjurationSpell.CONJURE_ANIMALS;
    spell.challengeRatings().stream()
        .forEach(cr -> System.out.println(cr + ": " + spell.generate(cr)));

    System.out.println();
    ConjurationSpell spell2 = ConjurationSpell.CONJURE_WOODLAND_BEINGS;
    spell2.challengeRatings().stream()
        .forEach(cr -> System.out.println(cr + ": " + spell2.generate(cr)));
  }
}
