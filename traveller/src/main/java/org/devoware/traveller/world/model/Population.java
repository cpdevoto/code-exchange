package org.devoware.traveller.world.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum Population implements DigitSource {
  _0(Digit._0, "No inhabitants."),
  _1(Digit._1, "Tens of inhabitants."),
  _2(Digit._2, "Hundreds of inhabitants."),
  _3(Digit._3, "Thousands of inhabitants."),
  _4(Digit._4, "Tens of thousands."),
  _5(Digit._5, "Hundreds of thousands."),
  _6(Digit._6, "Millions of inhabitants."),
  _7(Digit._7, "Tens of millions."),
  _8(Digit._8, "Hundreds of millions."),
  _9(Digit._9, "Billions of inhabitants."),
  A(Digit.A, "Tens of billions."),
  ;
  
  private static final Map<Digit, Population> BY_DIGIT;
  
  static {
    Map<Digit, Population> values = Maps.newHashMap();
    for (Population source : values()) {
      values.put(source.digit(), source);
    }
    BY_DIGIT = ImmutableMap.copyOf(values);

  }
  
  public static Population get(int value) {
    Digit digit = Digit.get(value);
    return (digit == null) ? null : BY_DIGIT.get(digit);
  }

  private final Digit digit;
  private final String summary;
  
  private Population(Digit digit, String summary) {
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
