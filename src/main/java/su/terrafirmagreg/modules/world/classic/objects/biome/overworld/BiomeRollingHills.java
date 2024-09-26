package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeRollingHills extends BaseBiome {

  public BiomeRollingHills() {
    super(new Settings("Rolling Hills")
            .guiColour(0x734B92)
            .baseHeight(-1.6000001f)
            .heightVariation(-2.3f)
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
      BiomeDictionary.Type.HILLS
    };
  }
}
