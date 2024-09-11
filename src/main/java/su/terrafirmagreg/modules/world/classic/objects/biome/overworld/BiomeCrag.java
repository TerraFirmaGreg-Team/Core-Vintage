package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiomeMesa;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeCrag extends BaseBiomeMesa {

  public BiomeCrag() {
    super(true, true, new Settings("Crag")
            .guiColour(0x090697)
            .baseHeight(-0.8F)
            .heightVariation(1.0F)
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
