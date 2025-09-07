package su.terrafirmagreg.framework.manager.content.base.generation.spi;

import su.terrafirmagreg.framework.manager.content.base.generation.api.IGeneratorEntry;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import lombok.Getter;

import java.util.Random;

@Getter
public abstract class BaseGenerator extends WorldGenerator implements IGeneratorEntry {

  protected final GeneratorSettings settings;

  public BaseGenerator() {
    this(GeneratorSettings.of());
  }

  public BaseGenerator(GeneratorSettings settings) {

    this.settings = settings;
  }

  @Override
  public boolean generate(World world, Random random, BlockPos pos) {
    return generate(world, random, pos, false);
  }

  public boolean generate(World world, Random random, BlockPos pos, boolean forced) {
    return false;
  }
}
