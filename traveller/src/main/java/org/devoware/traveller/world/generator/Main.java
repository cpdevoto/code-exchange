package org.devoware.traveller.world.generator;

import java.io.File;
import java.io.IOException;

import org.devoware.traveller.world.model.Sector;
import org.devoware.traveller.world.util.SectorPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    WorldGenerator worldGenerator = new WorldGenerator();
    SectorGenerator sectorGenerator = new SectorGenerator(worldGenerator);
    
    Sector sector = sectorGenerator.generate(StellarDensity.STANDARD);
    System.out.println("Sector world count: " + sector.worldCount());
    
    sector.subsectors().forEach((subsector) -> {
      log.debug("Subsector " + subsector.getIdentifier() + " world count: " + subsector.worldCount());
    });
    SectorPrinter.print(sector, new File("sector-data.dat"));
    
  }

}
