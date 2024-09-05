package su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.tree;

import su.terrafirmagreg.modules.world.classic.DataLayerClassic;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerBase;

public class GenLayerTreeInit extends GenLayerBase {

  private DataLayerClassic[] layerTrees;

  public GenLayerTreeInit(long par1, DataLayerClassic[] trees) {
    super(par1);
    this.layerTrees = trees.clone();
  }

  public int[] getInts(int par1, int par2, int maxX, int maxZ) {
    int[] cache = new int[maxX * maxZ];

    for (int z = 0; z < maxZ; ++z) {
      for (int x = 0; x < maxX; ++x) {
        this.initChunkSeed(par1 + x, par2 + z);
        cache[x + z * maxX] = this.layerTrees[this.nextInt(this.layerTrees.length)].layerID;
      }
    }

    return cache;
  }
}
