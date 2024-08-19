package su.terrafirmagreg.modules.world.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeHighHills extends BaseBiome {

    public BiomeHighHills() {
        super(new Settings("High Hills")
                .guiColour(0x920072)
                .baseHeight(-0.9000001f)
                .heightVariation(-1.1f)
                .enableWorldGen());
    }

    @Override
    public int getBiomeWeight() {
        return 0;
    }

    @Override
    public BiomeDictionary.Type[] getTypes() {

        return new BiomeDictionary.Type[] {
                BiomeDictionary.Type.HILLS
        };
    }
}
