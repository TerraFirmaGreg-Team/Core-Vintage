package su.terrafirmagreg.api.registry.spi;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

public interface IBiomeRegistry
        extends IBaseRegistry {

    default <B extends BaseBiome> B biome(B biome) {

        return biome(biome, biome.getRegistryKey());
    }

    default <B extends Biome> B biome(B biome, String name) {

        biome.setRegistryName(this.getModID(), name.replace(' ', '_').toLowerCase());

        this.getRegistry().getBiomes().add(biome);

        if (biome.ignorePlayerSpawnSuitability()) {
            BiomeManager.addSpawnBiome(biome);
        }

        //        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.WATER)) {
        //            // Register aquatic creatures
        //            biome.getSpawnableList(EnumCreatureType.WATER_CREATURE).add(new Biome.SpawnListEntry(EntitySquid.class, 20, 3, 7));
        //            // todo add fish (either in 1.15+ or if someone makes fish entities)
        //        }

        return biome;
    }
}
