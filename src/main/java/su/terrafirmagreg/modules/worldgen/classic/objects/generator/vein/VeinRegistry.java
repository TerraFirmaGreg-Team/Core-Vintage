package su.terrafirmagreg.modules.worldgen.classic.objects.generator.vein;

import su.terrafirmagreg.api.library.collection.WeightedCollection;
import su.terrafirmagreg.modules.worldgen.ModuleWorld;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum VeinRegistry {
  INSTANCE;

  private final WeightedCollection<VeinType> weightedVeinTypes = new WeightedCollection<>();
  private final Map<String, VeinType> veinTypeRegistry = new HashMap<>();

  @NotNull
  public WeightedCollection<VeinType> getVeins() {
    return weightedVeinTypes;
  }

  @NotNull
  public Set<String> keySet() {
    return veinTypeRegistry.keySet();
  }

  @Nullable
  public VeinType getVein(String name) {
    return veinTypeRegistry.get(name);
  }

  /**
   * Wraps things up and output to log
   */
  public void postInit() {
    // Reset max chunk radius and number of rolls for veins
    int maxRadius = 0;
    for (VeinType type : veinTypeRegistry.values()) {
      if (type.getWidth() > maxRadius) {
        maxRadius = type.getWidth();
      }
    }
    //GeneratorOreVeins.CHUNK_RADIUS = 1 + (maxRadius >> 4);
    ModuleWorld.LOGGER.info("Vein Registry Initialized, with {} veins, {} max radius, {} total weight", veinTypeRegistry.size(), maxRadius, weightedVeinTypes.getTotalWeight());
  }

}
