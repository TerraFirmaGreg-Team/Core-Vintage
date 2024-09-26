package su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.stability;

import su.terrafirmagreg.modules.world.classic.DataLayerClassic;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerBase;

import net.minecraft.world.gen.layer.IntCache;

public class GenLayerStabilityInit extends GenLayerBase {

  public GenLayerStabilityInit(long par1) {
    super(par1);
  }

  @Override
  public int[] getInts(int par1, int par2, int maxX, int maxZ) {
    int[] cache = IntCache.getIntCache(maxX * maxZ);

    for (int z = 0; z < maxZ; ++z) {
      for (int x = 0; x < maxX; ++x) {
        this.initChunkSeed(par1 + x, par2 + z);
        cache[x + z * maxX] = this.nextInt(3) == 0 ? DataLayerClassic.SEISMIC_UNSTABLE.layerID
                                                   : DataLayerClassic.SEISMIC_STABLE.layerID;
      }
    }

    return cache;
  }
}
