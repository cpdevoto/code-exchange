package org.devoware.traveller.world.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum Digit {
  _0(0),
  _1(1),
  _2(2),
  _3(3),
  _4(4),
  _5(5),
  _6(6),
  _7(7),
  _8(8),
  _9(9),
  A(10, "A"),
  B(11, "B"),
  C(12, "C"),
  D(13, "D"),
  E(14, "E"),
  F(15, "F"),
  G(16, "G"),
  H(17, "H"),
  J(18, "J"),
  K(19, "K"),
  L(20, "L"),
  M(21, "M"),
  X(Integer.MAX_VALUE, "X");
  
  private static final Map<Integer, Digit> DIGITS_BY_INT_VALUE;
  private static final Map<String, Digit> DIGITS_BY_STRING_VALUE;
  
  static {
    Map<Integer, Digit> digits1 = Maps.newHashMap();
    for (Digit digit : Digit.values()) {
      digits1.put(digit.value(), digit);
    }
    DIGITS_BY_INT_VALUE = ImmutableMap.copyOf(digits1);

    Map<String, Digit> digits2 = Maps.newHashMap();
    for (Digit digit : Digit.values()) {
      digits2.put(digit.toString(), digit);
    }
    DIGITS_BY_STRING_VALUE = ImmutableMap.copyOf(digits2);
  }
  
  public static Digit get(int value) {
    return DIGITS_BY_INT_VALUE.get(value);
  }
  
  public static Digit get(String value) {
    return DIGITS_BY_STRING_VALUE.get(value);
  }

  private final int value;
  private final String sValue;
  
  private Digit(int value) {
    this(value, String.valueOf(value));
  }
  
  private Digit(int value, String sValue) {
    this.value = value;
    this.sValue = sValue;
  }
  
  public int value() {
    return value;
  }
  
  public String toString() {
    return sValue;
  }

}
