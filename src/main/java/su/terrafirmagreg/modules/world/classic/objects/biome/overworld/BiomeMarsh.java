package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.modules.world.classic.objects.biome.overworld.decorator.BiomeDecorator;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeMarsh extends BaseBiome {

  public BiomeMarsh() {
    super(new Settings("Marsh")
            .guiColour(0x725742)
            .baseHeight(-1.9F)
            .heightVariation(-2.95F)
            .spawnBiome()
            .enableWorldGen());
  }

  @Override
  public net.minecraft.world.biome.BiomeDecorator createBiomeDecorator() {
    return new BiomeDecorator(8, 20);
  }

  @Override
  public int getBiomeWeight() {
    return 0;
  }

  @Override
  public BiomeDictionary.Type[] getTypes() {

    return new BiomeDictionary.Type[]{
      BiomeDictionary.Type.SWAMP,
      BiomeDictionary.Type.RIVER,
      BiomeDictionary.Type.WET,
      BiomeDictionary.Type.WATER,
      BiomeDictionary.Type.LUSH
    };
  }
}
