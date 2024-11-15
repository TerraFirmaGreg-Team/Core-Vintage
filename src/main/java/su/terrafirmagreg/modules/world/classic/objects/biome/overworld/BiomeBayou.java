package su.terrafirmagreg.modules.world.classic.objects.biome.overworld;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.BiomeDictionary;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.api.base.biome.BaseBiomeDecorator;

public class BiomeBayou extends BaseBiome {

  public BiomeBayou() {
    super(new Settings("Bayou")
            .guiColour(0x975545)
            .baseHeight(-2.21F)
            .heightVariation(-2.75F)
            .waterColor(16767282)
            .spawnBiome()
            .enableWorldGen());
  }

  @Override
  public BiomeDecorator createBiomeDecorator() {
    return new BaseBiomeDecorator(16, 45);
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
