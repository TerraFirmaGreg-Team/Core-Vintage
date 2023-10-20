package net.dries007.tfc.world.classic.genlayers.ridge;

import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nonnull;

public class GenLayerRidgeInitTFC extends GenLayerTFC {
    public GenLayerRidgeInitTFC(long par1, GenLayer par3GenLayer) {
        super(par1);
        this.parent = par3GenLayer;
    }

    @Nonnull
    @Override
    public int[] getInts(int xCoord, int zCoord, int xSize, int zSize) {
        int areaRadius = 14;
        int peakRadius = 2;
        int rangeRadius = 9;
        int parentXCoord = xCoord - areaRadius;
        int parentZCoord = zCoord - areaRadius;
        int parentXSize = xSize + 2 * areaRadius;
        int parentZSize = zSize + 2 * areaRadius;
        int[] parentCache = this.parent.getInts(parentXCoord, parentZCoord, parentXSize, parentZSize);
        int[] outCache = IntCache.getIntCache(xSize * zSize);

        for (int z = 0; z < zSize; ++z) {
            for (int x = 0; x < xSize; ++x) {
                this.initChunkSeed(x + xCoord, z + zCoord);
                boolean legal1 = true;
                boolean legal2 = true;
                boolean legal3 = true;

                int var10000;
                int rX;
                for (rX = 0; rX < areaRadius * 2 + 1 && (legal1 || legal2 || legal3); ++rX) {
                    for (int rZ = 0; rZ < areaRadius * 2 + 1 && (legal1 || legal2 || legal3); ++rZ) {
                        var10000 = parentCache[x + rX + (z + rZ) * parentXSize];
                        if (Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= areaRadius && legal1) {
                            legal1 = true;
                        }

                        if (Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= peakRadius && legal2) {
                            legal2 = true;
                        }

                        if (Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= rangeRadius && legal3) {
                            legal3 = true;
                        }
                    }
                }

                rX = x + z * xSize;
                var10000 = parentCache[rX];
                if (legal2) {
                    outCache[rX] = 3;
                } else if (legal3) {
                    outCache[rX] = 2;
                } else if (legal1) {
                    outCache[rX] = 1;
                } else {
                    outCache[rX] = 0;
                }
            }
        }

        return outCache;
    }
}
