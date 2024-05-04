package su.terrafirmagreg.api.spi.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;


import java.util.Random;

public abstract class BaseBiome extends Biome {

    public BaseBiome(BiomeProperties properties) {

        super(properties);
    }

    public abstract int getBiomeWeight();

    public abstract BiomeDictionary.Type[] getTypes();

    public BaseBiome mutate(Random rand) {

        return this;
    }
}
