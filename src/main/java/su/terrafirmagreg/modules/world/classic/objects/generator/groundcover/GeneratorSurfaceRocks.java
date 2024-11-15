package su.terrafirmagreg.modules.world.classic.objects.generator.groundcover;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.world.ConfigWorld;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

public class GeneratorSurfaceRocks
  implements IWorldGenerator {

  protected double factor;

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
                       IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

    if (ConfigWorld.MISC.enableLooseRocks) {
      // Проверяем, является ли генератор чанков экземпляром ChunkGenClassic и находится ли мир в измерении 0
      if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0) {
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        final var baseChunkData = CapabilityChunkData.get(world, chunkBlockPos);

        // Получаем правильный список камней
        int xoff = chunkX * 16 + 8;
        int zoff = chunkZ * 16 + 8;

        // Генерируем лежачии камни в заданном количестве
        for (int i = 0; i < ConfigWorld.MISC.looseRocksFrequency; i++) {
          var pos = new BlockPos(
            xoff + random.nextInt(16),
            0,
            zoff + random.nextInt(16)
          );
          var rock = baseChunkData.getRock1(pos);
          generateRock(random, world, pos.up(world.getTopSolidOrLiquidBlock(pos).getY()), rock);
        }
      }
    }
  }

  protected void generateRock(Random random, World world, BlockPos pos, RockType type) {
    // Используем воздух, чтобы не заменять другие генерируемые блоки
    // Это соответствует проверке в BlockPlacedItemFlat, если блок может оставаться
    // Также добавляем только на почву, так как это вызывается обработчиком регенерации мира позже

    if (world.isAirBlock(pos) && world.getBlockState(pos.down())
                                      .isSideSolid(world, pos.down(), EnumFacing.UP) &&
        BlockHelper.isSoil(world.getBlockState(pos.down()))) {
      world.setBlockState(pos,
                          BlocksRock.SURFACE.get(type).getDefaultState()
                                            .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(random.nextInt(4))), 2);
    }
  }

}
