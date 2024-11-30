package su.terrafirmagreg.modules.worldgen.classic;

import su.terrafirmagreg.api.base.biome.BaseBiomeProvider;
import su.terrafirmagreg.modules.worldgen.classic.objects.layer.GenLayerBase;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;

public class BiomeProviderClassic extends BaseBiomeProvider {

  public BiomeProviderClassic(World world) {
    super(world.getWorldInfo());

    if (!(world.getWorldType() instanceof WorldTypeClassic)) {
      throw new RuntimeException("Terrible things have gone wrong here.");
    }
  }

  /**
   * This is where we do the actual override of the generation, we discard the original and insert our own.
   */
  @Override
  public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
    return GenLayerBase.initializeBiomes(seed);
  }
}
