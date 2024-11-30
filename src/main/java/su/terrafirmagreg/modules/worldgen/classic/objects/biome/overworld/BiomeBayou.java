package su.terrafirmagreg.modules.worldgen.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.modules.worldgen.classic.objects.biome.overworld.decorator.BiomeDecorator;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeBayou extends BaseBiome {

  public BiomeBayou() {
    super(Settings.of("Bayou"));
    getSettings()
      .guiColour(0x975545)
      .baseHeight(-2.21F)
      .heightVariation(-2.75F)
      .waterColor(16767282)
      .spawnBiome()
      .biomeWeight(0)
      .enableWorldGen()
      .addType(Type.SWAMP, Type.RIVER, Type.WET, Type.WATER, Type.LUSH);
  }

  @Override
  public BiomeDecorator createBiomeDecorator() {
    return new BiomeDecorator(16, 45);
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
