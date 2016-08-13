package org.devoware.traveller.world.dice;

import java.util.Map;

import com.google.common.collect.Maps;

public class Literal implements ValueSource {
  private static Map<Integer, Literal> cache = Maps.newHashMap();
  
  private final int value;
  
  public static Literal valueOf(int value) {
    Literal literal = cache.get(value);
    if (literal == null) {
      literal = new Literal(value);
      cache.put(value, literal);
    }
    return literal;
  }

  private Literal(int value) {
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

}
