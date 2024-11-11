package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

import static su.terrafirmagreg.modules.rock.init.BlocksRock.SAND;

public class GeneratorSand extends WorldGenerator {

  private final int radius;

  public GeneratorSand(int radius) {
    this.radius = radius;
  }

  @Override
  public boolean generate(World world, Random rng, BlockPos pos) {
    if (BlockHelper.isWater(world.getBlockState(pos))) {
      return false;
    }

    final var sand = BlocksRock.SAND.get(ProviderChunkData.getRock1(world, pos));
    final int rnd = rng.nextInt(this.radius - 2) + 2;

    for (int x = -rnd; x <= rnd; x++) {
      for (int z = -rnd; z <= rnd; z++) {
        if (x * x + z * z > rnd * rnd) {
          continue;
        }
        for (int y = -2; y <= 2; y++) {
          final IBlockState state = world.getBlockState(pos.add(x, y, z));
          if (BlockHelper.isSoil(state) || Variant.isVariant(state, SAND)) {
            world.setBlockState(pos.add(x, y, z), sand.getDefaultState(), 2);
          }
        }
      }
    }

    return true;
  }
}
