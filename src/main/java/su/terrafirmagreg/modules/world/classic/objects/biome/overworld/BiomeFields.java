package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import net.minecraftforge.common.BiomeDictionary;

import su.terrafirmagreg.api.base.biome.BaseBiome;

public class BiomeFields extends BaseBiome {

  public BiomeFields() {
    super(new Settings("Fields")
            .guiColour(0x013240)
            .baseHeight(-1.7F)
            .heightVariation(-2.88F)
            .spawnBiome()
            .enableWorldGen());
  }

  @Override
  public int getBiomeWeight() {
    return 0;
  }

  @Override
  public BiomeDictionary.Type[] getTypes() {

    return new BiomeDictionary.Type[]{
      BiomeDictionary.Type.PLAINS,
      BiomeDictionary.Type.LUSH
    };
  }
}
