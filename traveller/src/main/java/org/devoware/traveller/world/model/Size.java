package org.devoware.traveller.world.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum Size implements DigitSource {
  _0(Digit._0, "Asteroid/Planetoid Belt"),
  _1(Digit._1, "1000 miles (1600 km)"),
  _2(Digit._2, "2000 miles (3200 km)"),
  _3(Digit._3, "3000 miles (4800 km)"),
  _4(Digit._4, "4000 miles (6400 km)"),
  _5(Digit._5, "5000 miles (8000 km)"),
  _6(Digit._6, "6000 miles (9600 km)"),
  _7(Digit._7, "7000 miles (11200 km)"),
  _8(Digit._8, "8000 miles (12800 km)"),
  _9(Digit._9, "9000 miles (14400 km)"),
  A(Digit.A, "10000 miles (16000 km)"),
  ;
  
  private static final Map<Digit, Size> BY_DIGIT;
  
  static {
    Map<Digit, Size> values = Maps.newHashMap();
    for (Size source : values()) {
      values.put(source.digit(), source);
    }
    BY_DIGIT = ImmutableMap.copyOf(values);

  }
  
  public static Size get(int value) {
    Digit digit = Digit.get(value);
    return (digit == null) ? null : BY_DIGIT.get(digit);
  }

  private final Digit digit;
  private final String summary;
  
  private Size(Digit digit, String summary) {
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
