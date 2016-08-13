package org.devoware.traveller.world.generator;

import static java.util.Objects.requireNonNull;
import static org.devoware.traveller.world.dice.Dice._2D6;
import static org.devoware.traveller.world.dice.Die.D6;
import static org.devoware.traveller.world.util.WorldPrinter.prettyPrint;

import javax.annotation.Nonnull;

import org.devoware.traveller.world.model.Atmosphere;
import org.devoware.traveller.world.model.Digit;
import org.devoware.traveller.world.model.Government;
import org.devoware.traveller.world.model.Hex;
import org.devoware.traveller.world.model.Hydrographics;
import org.devoware.traveller.world.model.LawLevel;
import org.devoware.traveller.world.model.Population;
import org.devoware.traveller.world.model.Size;
import org.devoware.traveller.world.model.Starport;
import org.devoware.traveller.world.model.TechLevel;
import org.devoware.traveller.world.model.World;

public class WorldGenerator {

  public WorldGenerator() {
    // TODO Auto-generated constructor stub
  }
  
  public World generate(@Nonnull Hex hex) {
    requireNonNull(hex);
    Starport starport = Tables.STARPORT.get(_2D6.getValue());
    boolean navalBase = false;
    if (starport.digit().value() < Digit.C.value()) {
      navalBase = Tables.NAVAL_BASE.get(_2D6.getValue());
    }
    boolean scoutBase = false;
    if (starport != Starport.E && starport != Starport.X) {
      scoutBase = Tables.SCOUT_BASE.get(
          bound(2, 12, _2D6.getValue() + Tables.SCOUT_BASE_MODIFIERS.get(starport)));
    }
    boolean gasGiant = Tables.GAS_GIANT.get(_2D6.getValue());
    
    Size size = Size.get(_2D6.getValue() - 2);
    Atmosphere atmosphere = Atmosphere._0;
    if (size != Size._0) {
      atmosphere = Atmosphere.get(bound(0, 12, _2D6.getValue() - 7 + size.digit().value()));
    }
    Hydrographics hydrographics = Hydrographics._0;
    if (size != Size._0) {
      int atmosphereMod = Tables.HYDROSPHERE_MODIFIERS.get(atmosphere);
      hydrographics = Hydrographics.get(bound(0, 10, _2D6.getValue() - 7 + size.digit().value() + atmosphereMod));
    }
    
    Population population = Population.get(_2D6.getValue() - 2);
    
    Government government = Government.get(bound(0, 13, _2D6.getValue() - 7 + population.digit().value()));

    LawLevel lawLevel = LawLevel.get(bound(0, 9, _2D6.getValue() - 7 + government.digit().value()));
    
    int techLevelMod = Tables.TECH_LEVEL_MODS_1.get(starport);
    techLevelMod += Tables.TECH_LEVEL_MODS_2.get(size);
    techLevelMod += Tables.TECH_LEVEL_MODS_3.get(atmosphere);
    techLevelMod += Tables.TECH_LEVEL_MODS_4.get(hydrographics);
    techLevelMod += Tables.TECH_LEVEL_MODS_5.get(population);
    techLevelMod += Tables.TECH_LEVEL_MODS_6.get(government);

    TechLevel techLevel = TechLevel.get(bound(0, 21, D6.getValue() + techLevelMod));
    return new World(hex, starport, navalBase, scoutBase, gasGiant, size,
        atmosphere, hydrographics, population, government, lawLevel, techLevel);
  }

  private int bound(int lower, int upper, int value) {
    int result = Math.max(lower, value);
    return Math.min(upper, result);
  }
  
  public static void main(String[] args) {
    WorldGenerator generator = new WorldGenerator();
    World world = generator.generate(new Hex(1, 1));
    System.out.println(prettyPrint(world));
  }
  
}
