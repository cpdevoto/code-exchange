package org.devoware.traveller.world.model;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Set;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

public class Subsector {
  public static final int TOTAL_COLUMNS = 8;
  public static final int TOTAL_ROWS = 10;
  

  private SubsectorIdentifier id;
  private final Map<Hex, World> worlds;

  public Subsector(@Nonnull SubsectorIdentifier id, @Nonnull Map<Hex, World> worlds) {
    this.id = requireNonNull(id);
    this.worlds = ImmutableMap.copyOf(requireNonNull(worlds));
  }

  public SubsectorIdentifier getIdentifier () {
    return id;
  }
  
  public Set<World> worlds() {
    final Set<World> worlds = Sets.newLinkedHashSet();
    this.worlds.values().forEach((world) -> {
      worlds.add(world);
    });
    return worlds;
  }


  @CheckForNull
  public World world(@Nonnull Hex hex) {
    return worlds.get(requireNonNull(hex));
  }
  
  public int worldCount() {
    return worlds.size();
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((worlds == null) ? 0 : worlds.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Subsector other = (Subsector) obj;
    if (id != other.id)
      return false;
    if (worlds == null) {
      if (other.worlds != null)
        return false;
    } else if (!worlds.equals(other.worlds))
      return false;
    return true;
  }

  
}
