package org.devoware.traveller.world.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum Starport implements DigitSource {
  A(Digit.A, "Excellent quality installation. Refined fuel available. Annual maintenance and overhaul available. Shipyard capable of constructing starships and non-startships present. Naval base and/or scout base may be present."),
  B(Digit.B, "Good quality installation. Refined fuel available. Annual maintenance and overhaul available. Shipyard capable of constructing non-startships present. Naval base and/or scout base may be present."),
  C(Digit.C, "Routine quality installation. Only unrefined fuel available. Reasonable repair facilities present. Scout base may be present."),
  D(Digit.D, "Poor quality installation. Only unrefined fuel available. No repair or shipyard facilities present. Scout base may be present."),
  E(Digit.E, "Frontier installation. Essentially a marked spot of bedrock with no fuel, facilities, or bases present."),
  X(Digit.X, "No starport. No provision is made for any ship landings.")
  ;
  
  private static final Map<Digit, Starport> BY_DIGIT;
  
  static {
    Map<Digit, Starport> values = Maps.newHashMap();
    for (Starport source : values()) {
      values.put(source.digit(), source);
    }
    BY_DIGIT = ImmutableMap.copyOf(values);

  }
  
  public static Starport get(int value) {
    Digit digit = Digit.get(value);
    return (digit == null) ? null : BY_DIGIT.get(digit);
  }
  
  
  private final Digit digit;
  private final String description;
  
  private Starport(Digit digit, String description) {
    this.digit = digit;
    this.description = description;
  }
  
  public Digit digit() {
    return digit;
  }

  public String description() {
    return description;
  }

  public String toString() {
    return digit.toString();
  }
}
