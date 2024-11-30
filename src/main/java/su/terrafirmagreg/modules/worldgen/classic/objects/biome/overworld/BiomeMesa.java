package su.terrafirmagreg.modules.worldgen.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiomeMesa;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeMesa extends BaseBiomeMesa {

  public BiomeMesa() {
    super(false, false, new Settings("Mesa")
      .guiColour(0x470285)
      .baseHeight(-1.7F)
      .heightVariation(-1.4F)
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
      BiomeDictionary.Type.MESA,
      BiomeDictionary.Type.HOT,
      BiomeDictionary.Type.SANDY,
      BiomeDictionary.Type.DRY,
      BiomeDictionary.Type.LUSH
    };
  }
}
