package org.devoware.traveller.world.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum Hydrographics implements DigitSource {
  _0(Digit._0, "No free standing water. Desert."),
  _1(Digit._1, "10% water."),
  _2(Digit._2, "20% water."),
  _3(Digit._3, "30% water."),
  _4(Digit._4, "40% water."),
  _5(Digit._5, "50% water."),
  _6(Digit._6, "60% water."),
  _7(Digit._7, "70% water."),
  _8(Digit._8, "80% water."),
  _9(Digit._9, "90% water."),
  A(Digit.A, "No land masses. Water World."),
  ;
  
  private static final Map<Digit, Hydrographics> BY_DIGIT;
  
  static {
    Map<Digit, Hydrographics> values = Maps.newHashMap();
    for (Hydrographics source : values()) {
      values.put(source.digit(), source);
    }
    BY_DIGIT = ImmutableMap.copyOf(values);

  }
  
  public static Hydrographics get(int value) {
    Digit digit = Digit.get(value);
    return (digit == null) ? null : BY_DIGIT.get(digit);
  }

  private final Digit digit;
  private final String summary;
  
  private Hydrographics(Digit digit, String summary) {
    this.digit = digit;
    this.summary = summary;
  }
  
  public Digit digit() {
    return digit;
  }

  public String getSummary() {
    return summary;
  }

  public String toString() {
    return digit.toString();
  }
}
