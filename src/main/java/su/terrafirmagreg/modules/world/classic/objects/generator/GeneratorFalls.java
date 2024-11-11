package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static su.terrafirmagreg.modules.rock.init.BlocksRock.RAW;

public class GeneratorFalls implements IWorldGenerator {

  private final IBlockState block;
  private final int rarity;

  public GeneratorFalls(IBlockState blockIn, int rarity) {
    this.block = blockIn;
    this.rarity = rarity;
  }

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
                       IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    for (int k5 = 0; k5 < rarity; ++k5) {
      int x = random.nextInt(16) + 8;
      int z = random.nextInt(16) + 8;
      int y = random.nextInt(WorldTypeClassic.SEALEVEL - 50) + 30;
      BlockPos pos = new BlockPos(chunkX << 4, y, chunkZ << 4).add(x, 0, z);
      if (!Variant.isVariant(world.getBlockState(pos.down()), RAW) && !Variant.isVariant(world.getBlockState(pos.up()), RAW) && (
        !Variant.isVariant(world.getBlockState(pos)) || !world.isAirBlock(pos))) {
        continue;
      }
      int rawHorizontal = 0, airHorizontal = 0;
      for (EnumFacing facing : EnumFacing.HORIZONTALS) {
        if (world.isAirBlock(pos.offset(facing))) {
          airHorizontal++;
        } else if (Variant.isVariant(world.getBlockState(pos.offset(facing)), RAW)) {
          rawHorizontal++;
        }
        if (airHorizontal > 1) {
          break;
        }
      }
      if (rawHorizontal == 3 && airHorizontal == 1) {
        world.setBlockState(pos, block, 2);
        world.immediateBlockTick(pos, block, random);
      }
    }
  }
}
