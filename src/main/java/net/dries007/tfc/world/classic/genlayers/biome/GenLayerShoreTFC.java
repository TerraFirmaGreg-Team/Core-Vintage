/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.world.classic.genlayers.biome;

import net.minecraft.world.gen.layer.IntCache;

import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;

public class GenLayerShoreTFC extends GenLayerTFC {

  public GenLayerShoreTFC(long seed, GenLayerTFC parent) {
    super(seed);
    this.parent = parent;
  }

  public int[] getInts(int x, int z, int sizeX, int sizeZ) {
    int[] ints = this.parent.getInts(x - 1, z - 1, sizeX + 2, sizeZ + 2);
    int[] out = IntCache.getIntCache(sizeX * sizeZ);

    for (int zz = 0; zz < sizeZ; ++zz) {
      for (int xx = 0; xx < sizeX; ++xx) {
        this.initChunkSeed((long) (zz + x), (long) (xx + z));
        int thisID = ints[xx + 1 + (zz + 1) * (sizeX + 2)];
        if (!this.isOceanicBiome(thisID) && thisID != this.riverID && thisID != this.bayouID && thisID != this.marshID && thisID != this.swamplandID
            && thisID != this.highHillsID) {
          int zn = ints[xx + 1 + (zz + 1 - 1) * (sizeX + 2)];
          int xp = ints[xx + 1 + 1 + (zz + 1) * (sizeX + 2)];
          int xn = ints[xx + 1 - 1 + (zz + 1) * (sizeX + 2)];
          int zp = ints[xx + 1 + (zz + 1 + 1) * (sizeX + 2)];
          if (!this.isOceanicBiome(zn) && !this.isOceanicBiome(xp) && !this.isOceanicBiome(xn) && !this.isOceanicBiome(zp)) {
            out[xx + zz * sizeX] = thisID;
          } else if (thisID != this.bayouID && thisID != this.marshID && thisID != this.swamplandID) {
            out[xx + zz * sizeX] = this.isMountainBiome(thisID) ? this.gravelBeachID : this.beachID;
          } else {
            out[xx + zz * sizeX] = this.mangroveID;
          }
        } else {
          out[xx + zz * sizeX] = thisID;
        }
      }
    }

    return out;
  }
}
