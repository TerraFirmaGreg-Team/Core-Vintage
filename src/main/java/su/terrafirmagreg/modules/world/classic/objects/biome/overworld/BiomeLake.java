package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.api.base.biome.BaseBiomeDecorator;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.BiomeDictionary;


import static su.terrafirmagreg.modules.world.classic.init.BiomesWorld.OCEAN;

public class BiomeLake extends BaseBiome {

  public BiomeLake() {
    super(new Settings("Lake")
        .guiColour(0x5D8C8D)
        .baseHeight(-2.4f)
        .heightVariation(-2.5990001f)
        .baseBiome(OCEAN));
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

  @Override
  public BiomeDecorator createBiomeDecorator() {
    return new BaseBiomeDecorator(4, 5);
  }
}
