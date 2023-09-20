package net.dries007.tfc.world.classic.genlayers.biome;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.core.config.ConfigTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import java.util.Arrays;
import java.util.Objects;

public class GenLayerBiomeTFC extends GenLayerTFC {
    private final int[] biomes = BiomesTFC.getWorldGenBiomes().stream().mapToInt(Biome::getIdForBiome).toArray();

    public GenLayerBiomeTFC(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
        if (ConfigTFC.General.DEBUG.debugWorldGenSafe) {
            TerraFirmaCraft.LOGGER.info("Worldgen biome list (ints): {}", biomes);
            TerraFirmaCraft.LOGGER.info("Worldgen biome list (names): {}", (Object) Arrays.stream(biomes).mapToObj(Biome::getBiomeForId).map(Objects::toString).toArray());
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
                if (isOceanicBiome(id)) out[xx + yy * sizeX] = id;
                else out[xx + yy * sizeX] = biomes[nextInt(biomes.length)];
            }
        }
        return out;
    }
}
