package org.devoware.traveller.world.dice;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum Die implements ValueSource {
  D4(4), D6(6), D8(8), D10(10), D12(12), D20(20);

  private static final Map<Integer, Die> DIE_MAP;

  private int type;

  static {
    Map<Integer, Die> dieMap = Maps.newHashMap();
    for (Die die : values()) {
      dieMap.put(die.type, die);
    }
    DIE_MAP = ImmutableMap.copyOf(dieMap);
  }

  public static Die get(int type) {
    return DIE_MAP.get(type);
  }

  private Die(int type) {
    this.type = type;
  }

  @Override
  public int getValue() {
    return (int) (Math.random() * type + 1);
  }

  public String toString() {
    return "D" + type;
  }

}
