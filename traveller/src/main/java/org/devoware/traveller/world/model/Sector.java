package org.devoware.traveller.world.model;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Set;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Sector {

  private final Map<SubsectorIdentifier, Subsector> subsectors = Maps.newTreeMap();

  public Sector(@Nonnull Set<Subsector> subs) {
    requireNonNull(subs);
    subs.forEach((s) -> subsectors.put(s.getIdentifier(), s));
  }
  
  @Nonnull
  public Subsector subsector(SubsectorIdentifier id) {
    return subsectors.get(id);
  }
  
  @Nonnull
  public Set<Subsector> subsectors() {
    return Sets.newLinkedHashSet(subsectors.values());
  }
  
  @Nonnull
  public Set<World> worlds() {
    final Set<World> worlds = Sets.newLinkedHashSet();
    SectorGrid.getHexes().forEach((hex) -> {
      SubsectorIdentifier id = SectorGrid.getSubsector(hex);
      Subsector subsector = subsectors.get(id);
      worlds.addAll(subsector.worlds());
    });
    return worlds;
  }

  @CheckForNull
  public World world(@Nonnull Hex hex) {
    requireNonNull(hex);
    SubsectorIdentifier id = SectorGrid.getSubsector(hex);
    Subsector subsector = subsectors.get(id);
    return subsector.world(hex);     
  }
  
  public int worldCount() {
    return subsectors.values().stream()
        .mapToInt(Subsector::worldCount)
        .sum();
  }
}
