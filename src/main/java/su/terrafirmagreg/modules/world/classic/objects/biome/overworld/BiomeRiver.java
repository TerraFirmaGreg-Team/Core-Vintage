package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeRiver extends BaseBiome {

  public BiomeRiver() {
    super(new Settings("River")
        .guiColour(0x2B8CBA)
        .baseHeight(-2.3f)
        .heightVariation(-3f));
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
