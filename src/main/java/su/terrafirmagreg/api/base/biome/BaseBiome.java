package su.terrafirmagreg.api.base.biome;

import su.terrafirmagreg.api.base.biome.spi.IBiomeSettings;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;


import lombok.Getter;

import java.util.Random;

@Getter
public abstract class BaseBiome extends Biome implements IBiomeSettings {

    public final Settings settings;

    public BaseBiome(Settings settings) {

        super(settings.build());

        this.settings = settings;
    }

    public abstract int getBiomeWeight();

    public abstract BiomeDictionary.Type[] getTypes();

    public BaseBiome mutate(Random rand) {

        return this;
    }

}
