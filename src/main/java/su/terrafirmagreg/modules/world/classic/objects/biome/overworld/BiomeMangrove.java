package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.api.base.biome.BaseBiomeDecorator;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeMangrove extends BaseBiome {

  public BiomeMangrove() {
    super(new Settings("Mangrove")
        .guiColour(0x236369)
        .baseHeight(-2.21F)
        .heightVariation(-2.75F)
        .waterColor(16767282)
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
        BiomeDictionary.Type.SWAMP,
        BiomeDictionary.Type.OCEAN,
        BiomeDictionary.Type.WET,
        BiomeDictionary.Type.WATER,
        BiomeDictionary.Type.LUSH
    };
  }

  @Override
  public BiomeDecorator createBiomeDecorator() {
    return new BaseBiomeDecorator(16, 45);
  }
}
