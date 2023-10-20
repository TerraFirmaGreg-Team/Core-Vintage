package net.dries007.tfc.world.classic.genlayers.biome;

import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.minecraft.world.gen.layer.GenLayer;

public class GenLayerLakeShore extends GenLayerTFC {
    public GenLayerLakeShore(long seed, GenLayer par3GenLayer) {
        super(seed);
        super.parent = par3GenLayer;
    }

    public int[] getInts(int xCoord, int zCoord, int xSize, int zSize) {
        int areaRadius = 2;
        int parentXCoord = xCoord - areaRadius;
        int parentZCoord = zCoord - areaRadius;
        int parentXSize = xSize + 2 * areaRadius;
        int parentZSize = zSize + 2 * areaRadius;
        int[] parentCache = this.parent.getInts(parentXCoord, parentZCoord, parentXSize, parentZSize);
        int[] outCache = new int[(xSize + 0) * (zSize + 0)];

        for (int z = 0; z < zSize + 0; ++z) {
            for (int x = 0; x < xSize + 0; ++x) {
                int[][] areas = new int[areaRadius * 2 + 1][areaRadius * 2 + 1];
                boolean same = true;
                boolean initialVal = false;
                int initialValue = parentCache[x + areaRadius + (z + areaRadius) * (areaRadius * 2 + xSize)];
                if (initialValue != this.lakeID) {
                    outCache[x + z * xSize] = parentCache[x + areaRadius + (z + areaRadius) * (areaRadius * 2 + xSize)];
                } else {
                    for (int rX = 0; rX < areaRadius * 2 + 1 && same; ++rX) {
                        for (int rZ = 0; rZ < areaRadius * 2 + 1 && same; ++rZ) {
                            if (Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= areaRadius) {
                                same = this.isWaterBiome(parentCache[x + rX + (z + rZ) * parentXSize]);
                            }
                        }
                    }

                    if (same) {
                        outCache[x + z * xSize] = parentCache[x + areaRadius + (z + areaRadius) * (areaRadius * 2 + xSize)];
                    } else {
                        outCache[x + z * xSize] = this.lakeshoreID;
                    }
                }
            }
        }

        return outCache;
    }
}
