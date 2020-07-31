package models.dto;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum NoticeType {
  PROMOTIONAL(1, "promotional"), INFORMATIONAL(2, "informational");

  private static final Map<Long, NoticeType> TYPES_BY_ID;
  private static final Map<String, NoticeType> TYPES_BY_NAME;

  private final long id;
  private final String name;

  static {
    TYPES_BY_ID = Arrays.stream(NoticeType.values())
        .collect(Collectors.toMap(NoticeType::getId, Function.identity()));
    TYPES_BY_NAME = Arrays.stream(NoticeType.values())
        .collect(Collectors.toMap(n -> n.name.toLowerCase(), Function.identity()));
  }

  public static NoticeType get(long id) {
    return TYPES_BY_ID.get(id);
  }

  public static NoticeType get(String name) {
    if (name == null) {
      return null;
    }
    return TYPES_BY_NAME.get(name.toLowerCase());
  }

  private NoticeType(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

}
