package su.terrafirmagreg.modules.world.classic.objects.generator;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;

import net.minecraft.block.BlockDirectional;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


import com.eerussianguy.firmalife.registry.BlocksFL;
import net.dries007.tfc.util.climate.Climate;
import tfcflorae.TFCFlorae;

import java.util.Random;

public class GeneratorGourds implements IWorldGenerator {

  public static final float RAINFALL_MELON = 160;
  public static final float RAINFALL_PUMPKIN = 140;

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
      IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (!(chunkGenerator instanceof ChunkGenClassic) && !TFCFlorae.FirmaLifeAdded) {
      return;
    }

    generateMelons(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    generatePumpkins(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
  }

  private void generateMelons(Random random, int chunkX, int chunkZ, World world,
      IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    // Guarantees crop generation if possible (easier to balance by config file while also making it random)
    BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

    float temperature = Climate.getAvgTemp(world, chunkBlockPos);
    float rainfall = ProviderChunkData.getRainfall(world, chunkBlockPos);
    float floraDensity = ProviderChunkData.getFloraDensity(world, chunkBlockPos);

    if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0
        && random.nextInt(20) == 0 &&
        rainfall >= RAINFALL_MELON &&
        temperature >= 18f && floraDensity >= 0.3f) {
      int melonsInChunk = 3 + random.nextInt(8);
      for (int i = 0; i < melonsInChunk; i++) {
        final int x = (chunkX << 4) + random.nextInt(16) + 8;
        final int z = (chunkZ << 4) + random.nextInt(16) + 8;
        final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

        if (world.isAirBlock(pos) &&
            (BlockUtils.isSoil(world.getBlockState(pos.down())) || BlockUtils.isGround(
                world.getBlockState(pos.down())))) {
          final int rotationValue = random.nextInt(4);

          if (rotationValue == 0) {
            world.setBlockState(pos, BlocksFL.MELON_FRUIT.getDefaultState()
                .withProperty(BlockDirectional.FACING, EnumFacing.NORTH), 2);
          } else if (rotationValue == 1) {
            world.setBlockState(pos, BlocksFL.MELON_FRUIT.getDefaultState()
                .withProperty(BlockDirectional.FACING, EnumFacing.EAST), 2);
          } else if (rotationValue == 2) {
            world.setBlockState(pos, BlocksFL.MELON_FRUIT.getDefaultState()
                .withProperty(BlockDirectional.FACING, EnumFacing.SOUTH), 2);
          } else if (rotationValue == 3) {
            world.setBlockState(pos, BlocksFL.MELON_FRUIT.getDefaultState()
                .withProperty(BlockDirectional.FACING, EnumFacing.WEST), 2);
          }
        }
      }
    }
  }

  private void generatePumpkins(Random random, int chunkX, int chunkZ, World world,
      IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    // Guarantees crop generation if possible (easier to balance by config file while also making it random)
    BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

    float temperature = Climate.getAvgTemp(world, chunkBlockPos);
    float rainfall = ProviderChunkData.getRainfall(world, chunkBlockPos);
    float floraDensity = ProviderChunkData.getFloraDensity(world, chunkBlockPos);

    if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0
        && random.nextInt(20) == 0 &&
        rainfall >= RAINFALL_PUMPKIN &&
        temperature >= 10f && floraDensity >= 0.2f) {
      int pumpkinsInChunk = 3 + random.nextInt(8);
      for (int i = 0; i < pumpkinsInChunk; i++) {
        final int x = (chunkX << 4) + random.nextInt(16) + 8;
        final int z = (chunkZ << 4) + random.nextInt(16) + 8;
        final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

        if (world.isAirBlock(pos) &&
            (BlockUtils.isSoil(world.getBlockState(pos.down())) || BlockUtils.isGround(
                world.getBlockState(pos.down())))) {
          final int rotationValue = random.nextInt(4);

          if (rotationValue == 0) {
            world.setBlockState(pos, BlocksFL.PUMPKIN_FRUIT.getDefaultState()
                .withProperty(BlockDirectional.FACING, EnumFacing.NORTH), 2);
          } else if (rotationValue == 1) {
            world.setBlockState(pos, BlocksFL.PUMPKIN_FRUIT.getDefaultState()
                .withProperty(BlockDirectional.FACING, EnumFacing.EAST), 2);
          } else if (rotationValue == 2) {
            world.setBlockState(pos, BlocksFL.PUMPKIN_FRUIT.getDefaultState()
                .withProperty(BlockDirectional.FACING, EnumFacing.SOUTH), 2);
          } else if (rotationValue == 3) {
            world.setBlockState(pos, BlocksFL.PUMPKIN_FRUIT.getDefaultState()
                .withProperty(BlockDirectional.FACING, EnumFacing.WEST), 2);
          }
        }
      }
    }
  }
}
