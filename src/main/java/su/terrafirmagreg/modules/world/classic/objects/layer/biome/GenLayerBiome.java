package su.terrafirmagreg.modules.world.classic.objects.layer.biome;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.biome.BaseBiomeProvider;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerBase;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;


import java.util.Arrays;
import java.util.Objects;

public class GenLayerBiome extends GenLayerBase {

  private final int[] biomes = BaseBiomeProvider.WORLD_GEN_BIOMES.stream()
      .mapToInt(Biome::getIdForBiome).toArray();

  public GenLayerBiome(long seed, GenLayer parent) {
    super(seed);
    this.parent = parent;
    if (ConfigCore.MISC.DEBUG.debugWorldGenSafe) {
      TerraFirmaGreg.LOGGER.info("Worldgen biome list (ints): {}", biomes);
      TerraFirmaGreg.LOGGER.info("Worldgen biome list (names): {}", (Object) Arrays.stream(biomes)
          .mapToObj(Biome::getBiomeForId)
          .map(Objects::toString)
          .toArray());
    }
  }

  @Override
  public int[] getInts(int x, int y, int sizeX, int sizeY) {
    int[] ints = parent.getInts(x, y, sizeX, sizeY);
    int[] out = IntCache.getIntCache(sizeX * sizeY);

    for (int yy = 0; yy < sizeY; ++yy) {
      for (int xx = 0; xx < sizeX; ++xx) {
        initChunkSeed(xx + x, yy + y);
        int id = ints[xx + yy * sizeX];
        if (isOceanicBiome(id)) {
          out[xx + yy * sizeX] = id;
        } else {
          out[xx + yy * sizeX] = biomes[nextInt(biomes.length)];
        }
      }
    }
    return out;
  }
}
