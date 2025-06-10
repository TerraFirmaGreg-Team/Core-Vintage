package su.terrafirmagreg.framework.manager.registry.base.generation.spi;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class BaseGenerator extends WorldGenerator {

  @Override
  public final boolean generate(World world, Random random, BlockPos pos) {
    return generate(world, random, pos, false);
  }

  public boolean generate(World world, Random random, BlockPos pos, boolean forced) {
    return false;
  }
}
