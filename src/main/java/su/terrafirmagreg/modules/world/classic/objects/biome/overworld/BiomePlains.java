package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;

public class BiomePlains extends BaseBiome {

  public BiomePlains() {
    super(new Settings("Plains")
        .guiColour(0x346B25)
        .baseHeight(-1.6000001f)
        .heightVariation(-2.54f)
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
        BiomeDictionary.Type.PLAINS
    };
  }
}
