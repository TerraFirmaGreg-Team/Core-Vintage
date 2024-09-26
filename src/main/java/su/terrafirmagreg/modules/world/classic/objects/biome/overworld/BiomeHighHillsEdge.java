package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;

import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.HIGH_HILLS;

public class BiomeHighHillsEdge extends BaseBiome {

  public BiomeHighHillsEdge() {
    super(new Settings("High Hills Edge")
            .guiColour(0x92567C)
            .baseHeight(-1.5f)
            .heightVariation(-2.3f)
            .baseBiome(HIGH_HILLS));
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
