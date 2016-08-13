package org.devoware.traveller.world.util;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Nonnull;

import org.devoware.traveller.world.model.Hex;
import org.devoware.traveller.world.model.Sector;
import org.devoware.traveller.world.model.SectorGrid;
import org.devoware.traveller.world.model.World;

public class SectorPrinter {

  public static void print(@Nonnull Sector sector, @Nonnull File outputFile) throws IOException {
    requireNonNull(sector);
    requireNonNull(outputFile);
    try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
      for (Hex hex : SectorGrid.getHexes()) {
        World world = sector.world(hex);
        out.println(hex + ": " + (world != null ? world : ""));
      }
    }

  }
}
