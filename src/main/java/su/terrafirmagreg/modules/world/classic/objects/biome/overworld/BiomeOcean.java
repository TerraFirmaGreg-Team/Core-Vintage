package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeOcean extends BaseBiome {

  public BiomeOcean() {
    super(new Settings("Ocean")
            .guiColour(0x3232C8)
            .baseHeight(-2.6f)
            .heightVariation(-2.69999f)
            .enableWorldGen());
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
