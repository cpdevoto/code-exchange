package org.devoware.traveller.world.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum Government implements DigitSource {
  _0(Digit._0, "No government structure. In many cases, family bonds predominate."),
  _1(Digit._1, "Company/Corporation. Government by a company managerial elite; citizens are company employees."),
  _2(Digit._2, "Participating Democracy. Government by advice and consent of the citizen."),
  _3(Digit._3, "Self-Perpetuating Oligarchy. Government by a restricted minority, with little or no input from the masses."),
  _4(Digit._4, "Representative Democracy. Government by elected representatives."),
  _5(Digit._5, "Feudal Technocracy. Government by specific individuals for those who agree to be ruled. Relationships are based on the performance of technical activities which are mutually beneficial."),
  _6(Digit._6, "Captive Government. Government by an imposed leadership answerable to an outside group. A colony or conquered area."),
  _7(Digit._7, "Balkanization. No central ruling authority exists; rival governments compete for control."),
  _8(Digit._8, "Civil Service Bureaucracy. Government by agencies employing individuals selected for their expertise."),
  _9(Digit._9, "Impersonal Bureaucracy. Govern- ment by agencies which are insulated from the governed."),
  A(Digit.A, "Charismatic Dictator. Government by a single leader enjoying the confidence of the citizens."),
  B(Digit.B, "Non-Charismatic Leader. A previous charismatic dictatar has been replaced by a leader through normal channels."),
  C(Digit.C, "Charismatic Oligarchy. Government by a select group, organization, or class enjoying the overwhelming confidence of the citizenry."),
  D(Digit.D, "Religious Dictatorship. Govern- ment by a religious organization without regard to the specific needs of the citizenry."),
  ;
  
  private static final Map<Digit, Government> BY_DIGIT;
  
  static {
    Map<Digit, Government> values = Maps.newHashMap();
    for (Government source : values()) {
      values.put(source.digit(), source);
    }
    BY_DIGIT = ImmutableMap.copyOf(values);

  }
  
  public static Government get(int value) {
    Digit digit = Digit.get(value);
    return (digit == null) ? null : BY_DIGIT.get(digit);
  }

  private final Digit digit;
  private final String summary;
  
  private Government(Digit digit, String summary) {
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
