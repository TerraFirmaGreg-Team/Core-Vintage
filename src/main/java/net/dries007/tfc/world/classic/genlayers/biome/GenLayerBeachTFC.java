package net.dries007.tfc.world.classic.genlayers.biome;

import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nonnull;

public class GenLayerBeachTFC extends GenLayerTFC {
    public GenLayerBeachTFC(long seed, GenLayer par3GenLayer) {
        super(seed);
        this.parent = par3GenLayer;
    }

    @Nonnull
    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        int[] var6 = IntCache.getIntCache(par3 * par4);

        for (int var7 = 0; var7 < par4; ++var7) {
            for (int var8 = 0; var8 < par3; ++var8) {
                this.initChunkSeed(var7 + par1, var8 + par2);
                int var9 = var5[var8 + 1 + (var7 + 1) * (par3 + 2)];
                if (!isOceanicBiome(var9) && var9 != this.riverID && var9 != this.riverBankID && var9 != this.swamplandID && var9 != this.mangroveID && var9 != this.highHillsID) {
                    int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                    int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                    int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                    int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                    if (!isOceanicBiome(var10) && !isOceanicBiome(var11) && !isOceanicBiome(var12) && !isOceanicBiome(var13)) {
                        var6[var8 + var7 * par3] = var9;
                    } else {
                        int beachid = this.beachID;
                        if (this.isMountainBiome(var9)) {
                            beachid = this.gravelBeachID;
                        }

                        var6[var8 + var7 * par3] = beachid;
                    }
                } else {
                    var6[var8 + var7 * par3] = var9;
                }

                validateInt(var6, var8 + var7 * par3);
            }
        }

        return var6;
    }
}
