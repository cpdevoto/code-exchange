package org.devoware.traveller.world.model;

import static com.google.common.base.Preconditions.checkArgument;

public class Hex implements Comparable<Hex> {

  private final int column;
  private final int row;
  private final String sColumn;
  private final String sRow;
  
  public Hex(int column, int row) {
    checkArgument(column >= 1 && column <= 32, "column must be between 01 and 32");
    checkArgument(row >= 1 && row <= 40, "row must be between 01 and 40");
    this.column = column;
    this.row = row;
    this.sColumn = (column < 10 ? "0" : "") + column;
    this.sRow = (row < 10 ? "0" : "") + row;
  }

  public int getColumn() {
    return column;
  }

  public int getRow() {
    return row;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + column;
    result = prime * result + row;
    result = prime * result + ((sColumn == null) ? 0 : sColumn.hashCode());
    result = prime * result + ((sRow == null) ? 0 : sRow.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Hex other = (Hex) obj;
    if (column != other.column)
      return false;
    if (row != other.row)
      return false;
    if (sColumn == null) {
      if (other.sColumn != null)
        return false;
    } else if (!sColumn.equals(other.sColumn))
      return false;
    if (sRow == null) {
      if (other.sRow != null)
        return false;
    } else if (!sRow.equals(other.sRow))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return sColumn + sRow;
  }

  @Override
  public int compareTo(Hex hex) {
    int result = this.column - hex.column;
    if (result == 0) {
      result = this.row - hex.row;
    }
    return result;
  }

  
}
