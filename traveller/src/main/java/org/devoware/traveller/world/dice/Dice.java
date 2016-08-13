package org.devoware.traveller.world.dice;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Dice implements ValueSource {
  public static final Dice _2D6 = newDice().with(2).D(6).build();
  public static final Dice _3D6 = newDice().with(3).D(6).build();
  
  private final List<Die> dice;

  public static Builder newDice() {
    return new Builder();
  }

  private Dice(Builder builder) {
    this.dice = ImmutableList.copyOf(builder.dice);
  }

  @Override
  public int getValue() {
    int total = 0;
    for (Die die : dice) {
      total += die.getValue();
    }
    return total;
  }

  public static class Builder {
    private final List<Die> dice = Lists.newArrayList();

    public DieExpressionBuilder with(int numDice) {
      return new DieExpressionBuilder(numDice);
    }

    public Dice build() {
      return new Dice(this);
    }

    public class DieExpressionBuilder {
      private int numDice;

      private DieExpressionBuilder(int numDice) {
        checkArgument(numDice > 0);
        this.numDice = numDice;
      }

      public Builder D(int dieType) {
        Die die = Die.get(dieType);
        checkNotNull("Invalid die type " + dieType, die);
        for (int i = 0; i < numDice; i++) {
          dice.add(die);
        }
        return Builder.this;
      }
    }
  }

}
