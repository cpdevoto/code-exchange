package utils.auth;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Algorithm {
  RS256("RS256");

  private static final Map<String, Algorithm> ALGORITHMS;

  private String id;

  static {
    ALGORITHMS =
        Arrays.stream(values()).collect(Collectors.toMap(Algorithm::getId, Function.identity()));
  }

  public static Algorithm get(String id) {
    return ALGORITHMS.get(id);
  }

  private Algorithm(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }
}
