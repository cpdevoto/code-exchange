package models.dto;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum TagGroup {
  MERCHANT_CATEGORY(1, "merchant_category");

  private static final Map<Long, TagGroup> TAG_GROUPS_BY_ID;
  private static final Map<String, TagGroup> TAG_GROUPS_BY_NAME;

  private final long id;
  private final String name;

  static {
    TAG_GROUPS_BY_ID = Arrays.stream(TagGroup.values())
        .collect(Collectors.toMap(TagGroup::getId, Function.identity()));
    TAG_GROUPS_BY_NAME = Arrays.stream(TagGroup.values())
        .collect(Collectors.toMap(n -> n.name.toLowerCase(), Function.identity()));
  }

  public static TagGroup get(long id) {
    return TAG_GROUPS_BY_ID.get(id);
  }

  public static TagGroup get(String name) {
    if (name == null) {
      return null;
    }
    return TAG_GROUPS_BY_NAME.get(name.toLowerCase());
  }

  private TagGroup(int id, String name) {
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
