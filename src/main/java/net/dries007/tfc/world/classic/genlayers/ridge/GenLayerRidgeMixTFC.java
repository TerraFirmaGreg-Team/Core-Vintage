package net.dries007.tfc.world.classic.genlayers.ridge;

import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nonnull;

public class GenLayerRidgeMixTFC extends GenLayerTFC {
    private final GenLayer biomePatternGeneratorChain;
    private final GenLayer ridgePatternGeneratorChain;
    private int[] layerBiomes;
    private int[] layerRidges;
    private int[] layerOut;
    private int xn;
    private int xp;
    private int zn;
    private int zp;

    public GenLayerRidgeMixTFC(long par1, GenLayer par3GenLayer, GenLayer par4GenLayer) {
        super(par1);
        this.biomePatternGeneratorChain = par3GenLayer;
        this.ridgePatternGeneratorChain = par4GenLayer;
    }

    @Nonnull
    @Override
    public int[] getInts(int x, int z, int xSize, int zSize) {
        layerBiomes = this.biomePatternGeneratorChain.getInts(x, z, xSize, zSize);
        layerRidges = this.ridgePatternGeneratorChain.getInts(x, z, xSize, zSize);
        layerOut = IntCache.getIntCache(xSize * zSize);

        for (int zElement = 0; zElement < zSize; ++zElement) {
            for (int xElement = 0; xElement < xSize; ++xElement) {
                int index = xElement + zElement * xSize;
                int b = layerBiomes[index];
                int r = layerRidges[index];

                xn = index - 1;
                xp = index + 1;
                zn = index - zSize;
                zp = index + zSize;

                if (isOceanicBiome(b)) {
                    layerOut[index] = b;
                } else if (r > 0) {
                    layerOut[index] = r;
                } else {
                    layerOut[index] = b;
                }

                removeMountainRange(index, lakeID);
                removeMountainRange(index, lakeshoreID);
                removeMountainRange(index, oceanID);
                removeMountainRange(index, deepOceanID);
                removeMountainRange(index, riverID);
                removeMountainRange(index, riverBankID);
                removeMountainRange(index, beachID);
                removeMountainRange(index, gravelBeachID);
                removeMountainRange(index, bayouID);
                removeMountainRange(index, mangroveID);
                removeMountainRange(index, marshID);
                validateInt(layerOut, index);
            }
        }

        return layerOut.clone();
    }

    public void removeMountainRange(int index, int biomeToReplaceWith) {
        if (layerOut[index] == mountainRangeID || layerOut[index] == mountainRangeEdgeID || layerOut[index] == foothillsID) {
            if (inBounds(xn, layerBiomes) && layerBiomes[xn] == biomeToReplaceWith) {
                layerOut[index] = biomeToReplaceWith;
            }

            if (inBounds(zn, layerBiomes) && layerBiomes[zn] == biomeToReplaceWith) {
                layerOut[index] = biomeToReplaceWith;
            }

            if (inBounds(xp, layerBiomes) && layerBiomes[xp] == biomeToReplaceWith) {
                layerOut[index] = biomeToReplaceWith;
            }

            if (inBounds(zp, layerBiomes) && layerBiomes[zp] == biomeToReplaceWith) {
                layerOut[index] = biomeToReplaceWith;
            }
        }
    }

    public boolean inBounds(int index, int[] array) {
        return index < array.length && index >= 0;
    }

    public void initWorldGenSeed(long par1) {
        biomePatternGeneratorChain.initWorldGenSeed(par1);
        ridgePatternGeneratorChain.initWorldGenSeed(par1);
        super.initWorldGenSeed(par1);
    }
}
