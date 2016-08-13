package org.devoware.traveller.world.util;

import static java.lang.String.format;

import java.util.Objects;

import javax.annotation.Nonnull;

import org.devoware.traveller.world.model.SectorGrid;
import org.devoware.traveller.world.model.SubsectorIdentifier;
import org.devoware.traveller.world.model.World;

public class WorldPrinter {
  
  public static String prettyPrint(@Nonnull World world) {
    Objects.requireNonNull(world);
    StringBuilder buf = new StringBuilder();
    SubsectorIdentifier subsectorId = SectorGrid.getSubsector(world.getHex());

    buf.append(format("UWP:           %s%n", world));
    buf.append(format("Subsector:     %s%n", subsectorId));
    buf.append(format("Hex:           %s%n", world.getHex()));
    buf.append(format("Starport:      %s - %s%n", world.getStarport(), world.getStarport().description()));
    buf.append(format("Size:          %s - %s%n", world.getSize(), world.getSize().getSummary()));
    buf.append(format("Atmosphere:    %s - %s%n", world.getAtmosphere(), world.getAtmosphere().getSummary()));
    buf.append(format("Hydrographics: %s - %s%n", world.getHydrographics(), world.getHydrographics().getSummary()));
    buf.append(format("Population:    %s - %s%n", world.getPopulation(), world.getPopulation().getSummary()));
    buf.append(format("Government:    %s - %s%n", world.getGovernment(), world.getGovernment().getSummary()));
    buf.append(format("Law Level:     %s - %s%n", world.getLawLevel(), world.getLawLevel().getSummary()));
    buf.append(format("Tech Level:    %s - %s%n", world.getTechLevel(), world.getTechLevel().getSummary()));
    buf.append(format("Gas Giant:     %s%n", bool(world.hasGasGiant())));
    buf.append(format("Naval Base:    %s%n", bool(world.hasNavalBase())));
    buf.append(format("Scout Base:    %s%n", bool(world.hasScoutBase())));
    return buf.toString();
  }
  
  private static String bool(boolean b) {
    if (b) {
      return "Yes";
    }
    return "No";
  }
  private WorldPrinter () {}

  
}
