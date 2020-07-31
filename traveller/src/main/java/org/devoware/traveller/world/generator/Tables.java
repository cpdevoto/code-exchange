package org.devoware.traveller.world.generator;

import static com.google.common.collect.Range.closed;
import static com.google.common.collect.Range.singleton;

import java.util.Map;
import java.util.function.Supplier;

import org.devoware.traveller.world.model.Atmosphere;
import org.devoware.traveller.world.model.Government;
import org.devoware.traveller.world.model.Hydrographics;
import org.devoware.traveller.world.model.Population;
import org.devoware.traveller.world.model.Size;
import org.devoware.traveller.world.model.Starport;

import com.google.common.collect.Maps;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

public class Tables {
  
  public static final RangeMap<Integer, Starport> STARPORT = supply(() -> {
      RangeMap<Integer, Starport> table = TreeRangeMap.create();
      table.put(closed(2, 4), Starport.A);
      table.put(closed(5, 6), Starport.B);
      table.put(closed(7, 8), Starport.C);
      table.put(singleton(9), Starport.D);
      table.put(closed(10, 11), Starport.E);
      table.put(singleton(12), Starport.X);
      return table;
    });

  public static final RangeMap<Integer, Boolean> NAVAL_BASE = supply(() -> {
    RangeMap<Integer, Boolean> table = TreeRangeMap.create();
    table.put(closed(2, 7), false);
    table.put(closed(8, 12), true);
    return table;
  });

  public static final RangeMap<Integer, Boolean> SCOUT_BASE = supply(() -> {
    RangeMap<Integer, Boolean> table = TreeRangeMap.create();
    table.put(closed(2, 6), false);
    table.put(closed(7, 12), true);
    return table;
  });
  
  public static final Map<Starport, Integer> SCOUT_BASE_MODIFIERS = supplyModifiers(() -> {
    Map<Starport, Integer> modifiers = Maps.newHashMap();
    for (Starport starport: Starport.values()) {
      modifiers.put(starport, 0);
    }
    modifiers.put(Starport.C, -1);
    modifiers.put(Starport.B, -2);
    modifiers.put(Starport.A, -3);
    return modifiers;
  });

  public static final RangeMap<Integer, Boolean> GAS_GIANT = supply(() -> {
    RangeMap<Integer, Boolean> table = TreeRangeMap.create();
    table.put(closed(2, 9), true);
    table.put(closed(10, 12), false);
    return table;
  });
  
  public static final Map<Atmosphere, Integer> HYDROSPHERE_MODIFIERS = supplyModifiers(() -> {
    Map<Atmosphere, Integer> modifiers = Maps.newHashMap();
    for (Atmosphere atmosphere: Atmosphere.values()) {
      modifiers.put(atmosphere, 0);
    }
    modifiers.put(Atmosphere._0, -4);
    modifiers.put(Atmosphere._1, -4);
    modifiers.put(Atmosphere.A, -4);
    modifiers.put(Atmosphere.B, -4);
    modifiers.put(Atmosphere.C, -4);
    return modifiers;
  });
  
  public static final Map<Starport, Integer> TECH_LEVEL_MODS_1 = supplyModifiers(() -> {
    Map<Starport, Integer> modifiers = Maps.newHashMap();
    for (Starport starport: Starport.values()) {
      modifiers.put(starport, 0);
    }
    modifiers.put(Starport.A, +6);
    modifiers.put(Starport.B, +4);
    modifiers.put(Starport.C, +2);
    modifiers.put(Starport.X, -4);
    return modifiers;
  });
  
  public static final Map<Size, Integer> TECH_LEVEL_MODS_2 = supplyModifiers(() -> {
    Map<Size, Integer> modifiers = Maps.newHashMap();
    for (Size size: Size.values()) {
      modifiers.put(size, 0);
    }
    modifiers.put(Size._0, +2);
    modifiers.put(Size._1, +2);
    modifiers.put(Size._2, +1);
    modifiers.put(Size._3, +1);
    modifiers.put(Size._4, +1);
    return modifiers;
  });

  public static final Map<Atmosphere, Integer> TECH_LEVEL_MODS_3 = supplyModifiers(() -> {
    Map<Atmosphere, Integer> modifiers = Maps.newHashMap();
    for (Atmosphere atmosphere: Atmosphere.values()) {
      modifiers.put(atmosphere, 0);
    }
    modifiers.put(Atmosphere._0, +1);
    modifiers.put(Atmosphere._1, +1);
    modifiers.put(Atmosphere._2, +1);
    modifiers.put(Atmosphere._3, +1);
    modifiers.put(Atmosphere.A, +1);
    modifiers.put(Atmosphere.B, +1);
    modifiers.put(Atmosphere.C, +1);
    return modifiers;
  });

  public static final Map<Hydrographics, Integer> TECH_LEVEL_MODS_4 = supplyModifiers(() -> {
    Map<Hydrographics, Integer> modifiers = Maps.newHashMap();
    for (Hydrographics key: Hydrographics.values()) {
      modifiers.put(key, 0);
    }
    modifiers.put(Hydrographics._9, +1);
    modifiers.put(Hydrographics.A, +1);
    return modifiers;
  });

  public static final Map<Population, Integer> TECH_LEVEL_MODS_5 = supplyModifiers(() -> {
    Map<Population, Integer> modifiers = Maps.newHashMap();
    for (Population key: Population.values()) {
      modifiers.put(key, 0);
    }
    modifiers.put(Population._1, +1);
    modifiers.put(Population._2, +1);
    modifiers.put(Population._3, +1);
    modifiers.put(Population._4, +1);
    modifiers.put(Population._5, +1);
    modifiers.put(Population._9, +2);
    modifiers.put(Population.A, +4);
    return modifiers;
  });

  public static final Map<Government, Integer> TECH_LEVEL_MODS_6 = supplyModifiers(() -> {
    Map<Government, Integer> modifiers = Maps.newHashMap();
    for (Government key: Government.values()) {
      modifiers.put(key, 0);
    }
    modifiers.put(Government._0, +1);
    modifiers.put(Government._5, +1);
    modifiers.put(Government.D, -2);
    return modifiers;
  });

  private static<T> RangeMap<Integer, T> supply(
      Supplier<RangeMap<Integer, T>> supplier) {
    return supplier.get();
  }

  private static <T> Map<T, Integer> supplyModifiers(
      Supplier<Map<T, Integer>> supplier) {
    return supplier.get();
  }

  private Tables () {}
}
