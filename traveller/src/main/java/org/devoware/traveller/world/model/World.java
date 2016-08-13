package org.devoware.traveller.world.model;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javax.annotation.Nonnull;

public class World {
  private final Hex hex;
  private final Starport starport;
  private final boolean navalBase;
  private final boolean scoutBase;
  private final boolean gasGiant;
  private final Size size;
  private final Atmosphere atmosphere;
  private final Hydrographics hydrographics;
  private final Population population;
  private final Government government;
  private final LawLevel lawLevel;
  private final TechLevel techLevel;
  

  public World(Hex hex, Starport starport, boolean navalBase, boolean scoutBase, boolean gasGiant,
      Size size, Atmosphere atmosphere, Hydrographics hydrographics, Population population,
      Government government, LawLevel lawLevel, TechLevel techLevel) {
    super();
    this.hex = requireNonNull(hex);
    this.starport = requireNonNull(starport);
    this.navalBase = navalBase;
    this.scoutBase = scoutBase;
    this.gasGiant = gasGiant;
    this.size = requireNonNull(size);
    this.atmosphere = requireNonNull(atmosphere);
    this.hydrographics = requireNonNull(hydrographics);
    this.population = requireNonNull(population);
    this.government = requireNonNull(government);
    this.lawLevel = requireNonNull(lawLevel);
    this.techLevel = requireNonNull(techLevel);
  }


  public Hex getHex() {
    return hex;
  }

  public Starport getStarport() {
    return starport;
  }

  public boolean hasNavalBase() {
    return navalBase;
  }

  public boolean hasScoutBase() {
    return scoutBase;
  }

  public boolean hasGasGiant() {
    return gasGiant;
  }

  public Size getSize() {
    return size;
  }

  public Atmosphere getAtmosphere() {
    return atmosphere;
  }

  public Hydrographics getHydrographics() {
    return hydrographics;
  }

  public Population getPopulation() {
    return population;
  }

  public Government getGovernment() {
    return government;
  }

  public LawLevel getLawLevel() {
    return lawLevel;
  }

  public TechLevel getTechLevel() {
    return techLevel;
  }

  @Override
  public String toString() {
    
    StringBuilder buf =  new StringBuilder(hex + " " + 
       starport + size + atmosphere + hydrographics + 
       population + government + lawLevel + " - " + techLevel);
    if (navalBase || scoutBase || gasGiant) {
      buf.append("   ");
      if (gasGiant) {
        buf.append("G ");
      }
      if (navalBase) {
        buf.append("N ");
      }
      if (scoutBase) {
        buf.append("S ");
      }
    }
    return buf.toString().trim();
  }
  
}
