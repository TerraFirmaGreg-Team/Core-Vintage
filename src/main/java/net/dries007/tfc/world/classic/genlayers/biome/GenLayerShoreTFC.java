/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.world.classic.genlayers.biome;

import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;

public class GenLayerShoreTFC extends GenLayerTFC {
    public GenLayerShoreTFC(long seed, GenLayerTFC parent) {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int xCoord, int zCoord, int xSize, int zSize) {
        int areaRadius = 8;
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
                if (initialValue != this.oceanID) {
                    outCache[x + z * xSize] = parentCache[x + areaRadius + (z + areaRadius) * (areaRadius * 2 + xSize)];
                } else {
                    for (int rX = 0; rX < areaRadius * 2 + 1 && same; ++rX) {
                        for (int rZ = 0; rZ < areaRadius * 2 + 1 && same; ++rZ) {
                            if (Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= areaRadius) {
                                same = parentCache[x + rX + (z + rZ) * parentXSize] != this.beachID && parentCache[x + rX + (z + rZ) * parentXSize] != this.gravelBeachID && parentCache[x + rX + (z + rZ) * parentXSize] != this.mangroveID;
                            }
                        }
                    }

                    if (same) {
                        outCache[x + z * xSize] = parentCache[x + areaRadius + (z + areaRadius) * (areaRadius * 2 + xSize)];
                    } else {
                        outCache[x + z * xSize] = this.shoreID;
                    }
                }
            }
        }

        return outCache;
    }
}
