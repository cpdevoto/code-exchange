package org.devoware.traveller.world.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum TechLevel implements DigitSource {
  _0(Digit._0, "Primitive (Stone Age)"),
  _1(Digit._1, "Bronze Age, Iron Age, Middle Ages (3500 BC - 600 AD)"),
  _2(Digit._2, "Age of Sail"),
  _3(Digit._3, "Industrial Revolution (1700 AD - 1850 AD)"),
  _4(Digit._4, "Mechanization (1900 AD)"),
  _5(Digit._5, "Mechanization (1930 AD)"),
  _6(Digit._6, "Nuclear Age (1950 AD)"),
  _7(Digit._7, "Nuclear Age (1975 AD)"),
  _8(Digit._8, "Nuclear Age (2000 AD)"),
  _9(Digit._9, "Nuclear Age (2050 AD). Jump 1."),
  A(Digit.A, "Nuclear Age (2100 AD). Jump 1."),
  B(Digit.B, "Imperial Average circa Year 0. Jump 2."),
  C(Digit.C, "Imperial Average circa Year 1 - Year 549. Jump 3."),
  D(Digit.D, "Imperial Maximum circa Year 550. Jump 4."),
  E(Digit.E, "Imperial Average circa Year 551 - Year 1106. Jump 5."),
  F(Digit.F, "Imperial Maximum circa Year 1107. Jump 6."),
  G(Digit.G, "Darrian Maximum. Jump 7."),
  H(Digit.H, "Above Darrian Maximum. Jump 8."),
  J(Digit.J, "Above Darrian Maximum. Jump 9."),
  K(Digit.K, "Far Far Future. Jump 9."),
  L(Digit.L, "Far Far Future. Jump 9."),
  M(Digit.M, "Far Far Future. Jump 9."),
  ;
  
  private static final Map<Digit, TechLevel> BY_DIGIT;
  
  static {
    Map<Digit, TechLevel> values = Maps.newHashMap();
    for (TechLevel source : values()) {
      values.put(source.digit(), source);
    }
    BY_DIGIT = ImmutableMap.copyOf(values);

  }
  
  public static TechLevel get(int value) {
    Digit digit = Digit.get(value);
    return (digit == null) ? null : BY_DIGIT.get(digit);
  }

  private final Digit digit;
  private final String summary;
  
  private TechLevel(Digit digit, String summary) {
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
