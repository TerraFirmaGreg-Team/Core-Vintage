package su.terrafirmagreg.modules.worldgen.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import static su.terrafirmagreg.modules.worldgen.classic.init.BiomesWorld.OCEAN;

public class BiomeDeepOcean extends BaseBiome {

  public BiomeDeepOcean() {
    super(Settings.of("Deep Ocean"));

    getSettings()
      .guiColour(0x000080)
      .baseHeight(-3.2f)
      .heightVariation(-2.49999f)
      .biomeWeight(0)
      .baseBiome(OCEAN)
      .addType(Type.OCEAN, Type.WET, Type.WATER);
  }

  @Override
  public int getBiomeWeight() {
    return 0;
  }

  @Override
  public BiomeDictionary.Type[] getTypes() {

    return new BiomeDictionary.Type[]{
      BiomeDictionary.Type.OCEAN,
      BiomeDictionary.Type.WET,
      BiomeDictionary.Type.WATER
    };
  }
}
