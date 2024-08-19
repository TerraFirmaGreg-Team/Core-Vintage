package su.terrafirmagreg.modules.world.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;


import static su.terrafirmagreg.modules.world.init.BiomesWorld.OCEAN;

public class BiomeDeepOcean extends BaseBiome {

    public BiomeDeepOcean() {
        super(new Settings("Deep Ocean")
                .guiColour(0x000080)
                .baseHeight(-3.2f)
                .heightVariation(-2.49999f)
                .baseBiome(OCEAN));
    }

    @Override
    public int getBiomeWeight() {
        return 0;
    }

    @Override
    public BiomeDictionary.Type[] getTypes() {

        return new BiomeDictionary.Type[] {
                BiomeDictionary.Type.OCEAN,
                BiomeDictionary.Type.WET,
                BiomeDictionary.Type.WATER
        };
    }
}
