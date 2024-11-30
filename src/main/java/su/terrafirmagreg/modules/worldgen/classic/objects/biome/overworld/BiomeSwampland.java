package su.terrafirmagreg.modules.worldgen.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.modules.worldgen.classic.objects.biome.overworld.decorator.BiomeDecorator;

import net.minecraftforge.common.BiomeDictionary;

public class BiomeSwampland extends BaseBiome {

  public BiomeSwampland() {
    super(Settings.of("Swampland"));
    getSettings()
      .guiColour(0x099200)
      .baseHeight(-1.8f)
      .heightVariation(-2.6000001f)
      .spawnBiome()
      .biomeWeight(0)
      .enableWorldGen();
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
      BiomeDictionary.Type.SWAMP
    };
  }
}
