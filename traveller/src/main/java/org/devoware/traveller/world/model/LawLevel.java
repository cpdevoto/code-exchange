package org.devoware.traveller.world.model;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public enum LawLevel implements DigitSource {
  _0(Digit._0, "No prohibitions."),
  _1(Digit._1, "Body pistols undetectable by standard detectors, explosives (bombs, grenades), and poison gas prohibited."),
  _2(Digit._2, "Portable energy weapons (laser carbine, laser rifle) prohibited. Ship's gunnery not affected."),
  _3(Digit._3, "Weapons of a strict military nature (machine guns, automatic rifles) prohibited."),
  _4(Digit._4, "Light assault weapons (submachineguns) prohibited."),
  _5(Digit._5, "Personal concealable firearms (such as pistols and revolvers) prohibited."),
  _6(Digit._6, "Most firearms (all except shotguns) prohibited. The carrying of any type of weapon openly is discouraged."),
  _7(Digit._7, "Shotguns are prohibited."),
  _8(Digit._8, "Long bladed weapons (all but daggers) are controlled, and open possession is prohibited."),
  _9(Digit._9, "Possession of any weapon outside one's residence is prohibited."),
  ;
  
  private static final Map<Digit, LawLevel> BY_DIGIT;
  
  static {
    Map<Digit, LawLevel> values = Maps.newHashMap();
    for (LawLevel source : values()) {
      values.put(source.digit(), source);
    }
    BY_DIGIT = ImmutableMap.copyOf(values);

  }
  
  public static LawLevel get(int value) {
    Digit digit = Digit.get(value);
    return (digit == null) ? null : BY_DIGIT.get(digit);
  }

  private final Digit digit;
  private final String summary;
  
  private LawLevel(Digit digit, String summary) {
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
