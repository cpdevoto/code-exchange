package org.devoware.traveller.world.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum Atmosphere implements DigitSource {
  _0(Digit._0, "No atmosphere."),
  _1(Digit._1, "Trace."),
  _2(Digit._2, "Very thin, tainted."),
  _3(Digit._3, "Very thin."),
  _4(Digit._4, "Thin, tainted."),
  _5(Digit._5, "Thin."),
  _6(Digit._6, "Standard."),
  _7(Digit._7, "Standard, tainted."),
  _8(Digit._8, "Dense."),
  _9(Digit._9, "Dense, tainted"),
  A(Digit.A, "Exotic"),
  B(Digit.B, "Corrosive"),
  C(Digit.C, "Insidious"),
  ;
  
  private static final Map<Digit, Atmosphere> BY_DIGIT;
  
  static {
    Map<Digit, Atmosphere> values = Maps.newHashMap();
    for (Atmosphere source : values()) {
      values.put(source.digit(), source);
    }
    BY_DIGIT = ImmutableMap.copyOf(values);

  }
  
  public static Atmosphere get(int value) {
    Digit digit = Digit.get(value);
    return (digit == null) ? null : BY_DIGIT.get(digit);
  }

  private final Digit digit;
  private final String summary;
  
  private Atmosphere(Digit digit, String summary) {
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
