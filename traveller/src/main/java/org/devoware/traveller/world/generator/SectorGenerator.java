package org.devoware.traveller.world.generator;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.devoware.traveller.world.model.Hex;
import org.devoware.traveller.world.model.Sector;
import org.devoware.traveller.world.model.SectorGrid;
import org.devoware.traveller.world.model.Subsector;
import org.devoware.traveller.world.model.SubsectorIdentifier;
import org.devoware.traveller.world.model.World;
import org.devoware.traveller.world.util.UwpParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class SectorGenerator {
  private static final Logger log = LoggerFactory.getLogger(SectorGenerator.class);
  
  private WorldGenerator worldGenerator;
  
  public SectorGenerator(@Nonnull WorldGenerator worldGenerator) {
    this.worldGenerator = requireNonNull(worldGenerator);
  }
  
  public Sector generate (@Nonnull StellarDensity stellarDensity) {
    requireNonNull(stellarDensity);
    Set<Subsector> subsectors = Sets.newLinkedHashSet();
    for (SubsectorIdentifier subsectorId : SubsectorIdentifier.values()) {
      log.debug("Generating worlds for subsector " + subsectorId);
      Map<Hex, World> worlds = Maps.newTreeMap();
      for (Hex hex : SectorGrid.getHexes(subsectorId)) {
        if (stellarDensity.test()) {
          World world = worldGenerator.generate(hex);
          worlds.put(hex, world);
          log.debug("Hex " + hex + ": " + world);
        } else {
          log.debug("Hex " + hex + ":");
        }
      }
      Subsector subsector = new Subsector(subsectorId, worlds);
      subsectors.add(subsector);
    }
    return new Sector(subsectors);
  }
  
  public Sector load (@Nonnull File dataFile) throws IOException {
    requireNonNull(dataFile);
    Set<Subsector> subsectors = Sets.newLinkedHashSet();
    Multimap<SubsectorIdentifier, World> worldsBySubsector = ArrayListMultimap.create();
    try (BufferedReader in = new BufferedReader(new FileReader(dataFile))) {
      for (String line = in.readLine(); line != null; line = in.readLine()) {
        if (line.trim().isEmpty()) {
          continue;
        }
        World world = UwpParser.parseSectorRow(line.trim());
        if (world == null) {
          continue;
        }
        SubsectorIdentifier subsectorId = SectorGrid.getSubsector(world.getHex());
        worldsBySubsector.put(subsectorId, world);
      }
    }
    for (SubsectorIdentifier subsectorId : worldsBySubsector.keySet()) {
      Map<Hex, World> worlds = Maps.newTreeMap();
      for (World world : worldsBySubsector.get(subsectorId)) {
        worlds.put(world.getHex(), world);
      }
      Subsector subsector = new Subsector(subsectorId, worlds);
      subsectors.add(subsector);
    }
    return new Sector(subsectors);
  }

}
