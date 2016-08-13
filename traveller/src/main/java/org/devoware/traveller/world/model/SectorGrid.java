package org.devoware.traveller.world.model;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collector;

import javax.annotation.Nonnull;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class SectorGrid {
  private static final int MAX_COLUMN = 32;
  private static final int MAX_ROW = 40;
  
  private static final Table<Integer, Integer, Hex> HEX_GRID;
  private static final Map<Hex, SubsectorIdentifier> SUBSECTORS_BY_HEX; 
  
  static {
    Table<Integer, Integer, Hex> hexGrid = HashBasedTable.create();
    for (int column = 1; column <= MAX_COLUMN; column++) {
      for (int row = 1; row <= MAX_ROW; row++) {
        hexGrid.put(row, column, new Hex(column, row));
        
      }
    }
    HEX_GRID = ImmutableTable.copyOf(hexGrid);
    Map<Hex, SubsectorIdentifier> subsectorsByHex = Maps.newHashMap();
    for (SubsectorIdentifier subsectorId : SubsectorIdentifier.values()) {
      for (Hex hex : getHexes(subsectorId)) {
        subsectorsByHex.put(hex, subsectorId);
      }
    }
    SUBSECTORS_BY_HEX = ImmutableMap.copyOf(subsectorsByHex);
  }
  
  public static Set<Hex> getHexes() {
    return HEX_GRID.cellSet().stream()
    .map(new Function<Cell<Integer, Integer, Hex>, Hex> () {

      @Override
      public Hex apply(Cell<Integer, Integer, Hex> cell) {
        return cell.getValue();
      }
      
    }).collect(hexCollector());
  }

  public static Set<Hex> getHexes(@Nonnull SubsectorIdentifier subsectorId) {
    requireNonNull(subsectorId);
    return getHexes().stream()
        .filter(subsectorId)
        .collect(hexCollector());
  }
  
  public static SubsectorIdentifier getSubsector(@Nonnull Hex hex) {
    return SUBSECTORS_BY_HEX.get(hex);
  }
  
  @Nonnull
  private static Collector<Hex, ?, TreeSet<Hex>> hexCollector() {
    return Collector.of(TreeSet::new, TreeSet::add, (left, right) -> { left.addAll(right); return left; });
  }

  private SectorGrid() {}

  
}
