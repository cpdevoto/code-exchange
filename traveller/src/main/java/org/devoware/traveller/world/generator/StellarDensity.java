package org.devoware.traveller.world.generator;

import static org.devoware.traveller.world.dice.Die.D6;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import org.devoware.traveller.world.dice.Dice;
import org.devoware.traveller.world.dice.ValueSource;

public enum StellarDensity {
  EXTRA_GALACTIC (Dice._3D6, (value) -> value <= 3),
  RIFT(Dice._2D6, (value) -> value <= 2),
  SPARSE(D6, (value) -> value <= 1),
  SCATTERED(D6, (value) -> value <= 2),
  STANDARD(D6, (value) -> value <= 3),
  DENSE(D6, (value) -> value <= 4),
  CLUSTER(D6, (value) -> value <= 5),
  CORE(Dice._2D6, (value) -> value <= 2),
  
  ;

  private final ValueSource valueSource;
  private final Predicate<Integer> predicate;
  
  private StellarDensity(@Nonnull ValueSource valueSource, @Nonnull Predicate<Integer> predicate) {
    this.valueSource = valueSource;
    this.predicate = predicate;
  }
  
  public boolean test () {
    return predicate.test(valueSource.getValue());
  }
}
