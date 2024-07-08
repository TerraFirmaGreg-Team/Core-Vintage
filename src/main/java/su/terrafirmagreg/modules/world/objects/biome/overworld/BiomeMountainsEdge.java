package su.terrafirmagreg.modules.world.objects.biome.overworld;

import su.terrafirmagreg.api.spi.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;


import static su.terrafirmagreg.modules.world.init.BiomesWorld.MOUNTAINS;

public class BiomeMountainsEdge extends BaseBiome {

    public BiomeMountainsEdge() {
        super(new Settings("Mountains Edge")
                .guiColour(0x924A4C)
                .baseHeight(-1.3f)
                .heightVariation(-1.9000001f)
                .baseBiome(MOUNTAINS)
                .spawnBiome());
    }

    @Override
    public int getBiomeWeight() {
        return 0;
    }

    @Override
    public BiomeDictionary.Type[] getTypes() {

        return new BiomeDictionary.Type[] {
                BiomeDictionary.Type.MOUNTAIN
        };
    }
}
