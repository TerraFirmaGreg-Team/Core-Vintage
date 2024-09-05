package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiomeMesa;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeMesaPlateau extends BaseBiomeMesa {

  public BiomeMesaPlateau() {
    super(false, false, new Settings("Mesa Plateau")
        .guiColour(0x470285)
        .baseHeight(-0.6F)
        .heightVariation(-2.63F)
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
