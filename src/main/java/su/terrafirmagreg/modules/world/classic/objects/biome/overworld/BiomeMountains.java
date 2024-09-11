package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeMountains extends BaseBiome {

  public BiomeMountains() {
    super(new Settings("Mountains")
            .guiColour(0x920000)
            .baseHeight(-0.9000001f)
            .heightVariation(-1.1f)
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
            BiomeDictionary.Type.MOUNTAIN
    };
  }
}
