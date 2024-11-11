package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.util.climate.IceMeltHandler;

import java.util.Random;

public class GeneratorSnowIce implements IWorldGenerator {

  @Override
  public void generate(Random rand, int chunkX, int chunkZ, World world,
                       IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0) {
      for (int x = 0; x < 16; x++) {
        for (int z = 0; z < 16; z++) {
          int xoff = chunkX * 16 + 8 + x;
          int zoff = chunkZ * 16 + 8 + z;
          BlockPos pos = new BlockPos(xoff, 0, zoff);
          pos = pos.up(world.getPrecipitationHeight(pos).getY());

          // Can't use world#canBlockFreeze because it's specific to vanilla water
          BlockPos posDown = pos.down();
          IBlockState stateAt = world.getBlockState(posDown);
          float actualTemp = ClimateTFC.getActualTemp(world, posDown);
          if (actualTemp < IceMeltHandler.ICE_MELT_THRESHOLD - 4 + 4 * (rand.nextFloat()
                                                                        - rand.nextFloat()) &&
              stateAt.getBlock() == ChunkGenClassic.FRESH_WATER.getBlock()) {
            world.setBlockState(posDown, ChunkGenClassic.FRESH_WATER_ICE);
          }

          if (world.isAirBlock(pos) && ChunkGenClassic.SNOW.getBlock()
                                                           .canPlaceBlockAt(world, pos) && actualTemp < -4 + 4 * (rand.nextFloat()
                                                                                                                  - rand.nextFloat())) {
            world.setBlockState(pos, ChunkGenClassic.SNOW);
          }
        }
      }
    }
  }
}
