package su.terrafirmagreg.modules.worldgen.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiomeMesa;

import net.minecraftforge.common.BiomeDictionary;

import static su.terrafirmagreg.modules.worldgen.classic.init.BiomesWorld.MESA_PLATEAU;

public class BiomeMesaPlateauM extends BaseBiomeMesa {

  public BiomeMesaPlateauM() {
    super(false, false, new Settings("Mesa Plateau M")
      .guiColour(0x470285)
      .baseHeight(-0.1F)
      .heightVariation(-1.5F)
      .baseBiome(MESA_PLATEAU)
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
      BiomeDictionary.Type.DRY,
      BiomeDictionary.Type.SPARSE,
      BiomeDictionary.Type.MOUNTAIN
    };
  }
}
