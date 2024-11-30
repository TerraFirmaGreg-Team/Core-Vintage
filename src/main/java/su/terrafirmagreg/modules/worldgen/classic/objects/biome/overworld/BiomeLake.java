package su.terrafirmagreg.modules.worldgen.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.modules.worldgen.classic.objects.biome.overworld.decorator.BiomeDecorator;

import net.minecraftforge.common.BiomeDictionary;

import static su.terrafirmagreg.modules.worldgen.classic.init.BiomesWorld.OCEAN;

public class BiomeLake extends BaseBiome {

  public BiomeLake() {
    super(new Settings("Lake")
            .guiColour(0x5D8C8D)
            .baseHeight(-2.4f)
            .heightVariation(-2.5990001f)
            .baseBiome(OCEAN));
  }

  @Override
  public BiomeDecorator createBiomeDecorator() {
    return new BiomeDecorator(4, 5);
  }

  @Override
  public int getBiomeWeight() {
    return 0;
  }

  @Override
  public BiomeDictionary.Type[] getTypes() {

    return new BiomeDictionary.Type[]{
      BiomeDictionary.Type.RIVER,
      BiomeDictionary.Type.WET,
      BiomeDictionary.Type.WATER
    };
  }
}
