package su.terrafirmagreg.api.base.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.storage.WorldInfo;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseBiomeProvider extends BiomeProvider {

  public static final List<Biome> WORLD_GEN_BIOMES = new ArrayList<>();

  public BaseBiomeProvider(WorldInfo worldInfo) {
    super(worldInfo);
  }
}
