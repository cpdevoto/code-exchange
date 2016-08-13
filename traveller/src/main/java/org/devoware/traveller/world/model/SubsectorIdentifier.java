package org.devoware.traveller.world.model;

import static org.devoware.traveller.world.model.Subsector.*;

import java.util.function.Predicate;

public enum SubsectorIdentifier implements Predicate<Hex> {
  A(0, 0), B(1, 0), C(2, 0), D(3, 0),
  E(0, 1), F(1, 1), G(2, 1), H(3, 1),
  I(0, 2), J(1, 2), K(2, 2), L(3, 2),
  M(0, 3), N(1, 3), O(2, 3), P(3, 3);
 
  private final int column;
  private final int row;
  private final int minHexColumn;
  private final int maxHexColumn;
  private final int minHexRow;
  private final int maxHexRow;
  
  private SubsectorIdentifier(int column, int row) {
    this.column = column;
    this.row = row;
    this.minHexColumn = (column * TOTAL_COLUMNS) + 1;
    this.maxHexColumn = ((column + 1) * TOTAL_COLUMNS);
    this.minHexRow = (row * TOTAL_ROWS) + 1;
    this.maxHexRow = ((row + 1) * TOTAL_ROWS);
  }
  
  public int getColumn() {
    return column;
  }

  public int getRow() {
    return row;
  }

  public int getMinHexColumn() {
    return minHexColumn;
  }

  public int getMaxHexColumn() {
    return maxHexColumn;
  }

  public int getMinHexRow() {
    return minHexRow;
  }

  public int getMaxHexRow() {
    return maxHexRow;
  }

  @Override
  public boolean test(Hex hex) {
     if (hex.getColumn() >= minHexColumn && hex.getColumn() <= maxHexColumn &&
         hex.getRow()    >= minHexRow    && hex.getRow() <= maxHexRow) {
       return true;
     }
     return false;
  }
}
