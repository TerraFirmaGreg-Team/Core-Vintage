package su.terrafirmagreg.api.base.object.biome.spi;

import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.storage.WorldInfo;

public abstract class BaseBiomeProvider extends BiomeProvider {

  public BaseBiomeProvider(WorldInfo worldInfo) {
    super(worldInfo);

  }

  // TODO: В тфк указывали конкретные биомы, тут надо подумать, нужно ли это, или указать конкретный список биомов для спавна
//  @Override
//  public List<Biome> getBiomesToSpawnIn() {
//    return SPAWN_BIOMES;
//  }
}
