package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeHighPlains extends BaseBiome {

  public BiomeHighPlains() {
    super(new Settings("High Plains")
            .guiColour(0xC7A03B)
            .baseHeight(-1.3f)
            .heightVariation(-2.27f)
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
            BiomeDictionary.Type.HILLS,
            BiomeDictionary.Type.PLAINS
    };
  }
}
