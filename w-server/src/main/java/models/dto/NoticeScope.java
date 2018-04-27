package models.dto;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum NoticeScope {
  ALL_USERS(1, "all_users"), VIP_USERS(2, "vip_users");

  private static final Map<Long, NoticeScope> SCOPES_BY_ID;
  private static final Map<String, NoticeScope> SCOPES_BY_NAME;

  private final long id;
  private final String name;

  static {
    SCOPES_BY_ID = Arrays.stream(NoticeScope.values())
        .collect(Collectors.toMap(NoticeScope::getId, Function.identity()));
    SCOPES_BY_NAME = Arrays.stream(NoticeScope.values())
        .collect(Collectors.toMap(n -> n.name.toLowerCase(), Function.identity()));
  }

  public static NoticeScope get(long id) {
    return SCOPES_BY_ID.get(id);
  }

  public static NoticeScope get(String name) {
    if (name == null) {
      return null;
    }
    return SCOPES_BY_NAME.get(name.toLowerCase());
  }

  private NoticeScope(int id, String name) {
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
