package org.devoware.traveller.world.dice;

import static org.devoware.traveller.world.dice.Die.D6;

public interface ValueSource {
  
  public static final ValueSource FLUX = () -> {
    return D6.getValue() - D6.getValue();
  };
  
  public static final ValueSource GOOD_FLUX = () -> {
    int roll1 = D6.getValue();
    int roll2 = D6.getValue();
    if (roll1 > roll2) {
      return roll1 - roll2;
    }
    return roll2 - roll1;
  };
  public static final ValueSource BAD_FLUX = () -> {
    int roll1 = D6.getValue();
    int roll2 = D6.getValue();
    if (roll1 < roll2) {
      return roll1 - roll2;
    }
    return roll2 - roll1;
  };
  
  
  public int getValue();

}
