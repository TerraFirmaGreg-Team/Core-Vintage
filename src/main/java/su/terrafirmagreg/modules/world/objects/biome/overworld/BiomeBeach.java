package su.terrafirmagreg.modules.world.objects.biome.overworld;

import su.terrafirmagreg.api.spi.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeBeach extends BaseBiome {

    public BiomeBeach() {
        super(new Settings("Beach")
                .guiColour(0xC7A03B)
                .baseHeight(-1.69f)
                .heightVariation(-2.68f));
    }

    @Override
    public int getBiomeWeight() {
        return 0;
    }

    @Override
    public BiomeDictionary.Type[] getTypes() {

        return new BiomeDictionary.Type[] {
                BiomeDictionary.Type.BEACH
        };
    }
}
