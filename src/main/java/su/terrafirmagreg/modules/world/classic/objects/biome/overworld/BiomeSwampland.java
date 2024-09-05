package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.api.base.biome.BaseBiomeDecorator;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeSwampland extends BaseBiome {

  public BiomeSwampland() {
    super(new Settings("Swampland")
        .guiColour(0x099200)
        .baseHeight(-1.8f)
        .heightVariation(-2.6000001f)
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
        BiomeDictionary.Type.SWAMP
    };
  }

  @Override
  public BiomeDecorator createBiomeDecorator() {
    return new BaseBiomeDecorator(16, 45);
  }
}
