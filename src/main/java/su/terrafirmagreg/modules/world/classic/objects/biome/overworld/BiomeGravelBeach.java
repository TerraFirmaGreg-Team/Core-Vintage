package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;


import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.BEACH;

public class BiomeGravelBeach extends BaseBiome {

  public BiomeGravelBeach() {
    super(new Settings("Gravel Beach")
        .guiColour(0x7E7450)
        .baseHeight(-1.69f)
        .heightVariation(-2.68f)
        .enableWorldGen()
        .baseBiome(BEACH));
  }

  @Override
  public int getBiomeWeight() {
    return 0;
  }

  @Override
  public BiomeDictionary.Type[] getTypes() {

    return new BiomeDictionary.Type[]{
        BiomeDictionary.Type.BEACH
    };
  }
}
