package org.devoware.traveller.world.generator;

import java.io.File;
import java.io.IOException;

import org.devoware.traveller.world.model.Hex;
import org.devoware.traveller.world.model.Sector;
import org.devoware.traveller.world.model.SectorGrid;
import org.devoware.traveller.world.model.World;

public class Main2 {

  public static void main(String[] args) throws IOException {
    WorldGenerator worldGenerator = new WorldGenerator();
    SectorGenerator sectorGenerator = new SectorGenerator(worldGenerator);
    
    Sector sector = sectorGenerator.load(new File("sector-data.dat"));
    System.out.println("Sector world count: " + sector.worldCount());
    
    for (Hex hex : SectorGrid.getHexes()) {
      World world = sector.world(hex);
      System.out.println(hex + ": " + (world != null ? world : ""));
    }
    
  }

}
