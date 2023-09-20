package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.module.core.config.ConfigTFC;
import net.dries007.tfc.module.rock.common.RockStorage;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static net.dries007.tfc.module.rock.api.variant.block.RockBlockVariants.LOOSE_ROCK;
import static net.dries007.tfc.module.rock.common.blocks.BlockRockLoose.AXIS;

public class WorldGenLooseRocks implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (ConfigTFC.General.WORLD.enableLooseRocks) {
            // Проверяем, является ли генератор чанков экземпляром ChunkGenTFC и находится ли мир в измерении 0
            if (chunkGenerator instanceof ChunkGenTFC && world.provider.getDimension() == 0) {
                final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
                final ChunkDataTFC baseChunkData = ChunkDataTFC.get(world, chunkBlockPos);

                // Получаем правильный список камней
                int xoff = chunkX * 16 + 8;
                int zoff = chunkZ * 16 + 8;

                // Генерируем лежачии камни в заданном количестве
                for (int i = 0; i < ConfigTFC.General.WORLD.looseRocksFrequency; i++) {
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

        if (world.isAirBlock(pos) &&
                world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) &&
                TFCBlocks.isSoil(world.getBlockState(pos.down()))) {
            world.setBlockState(pos, RockStorage.getRockBlock(LOOSE_ROCK, type).getDefaultState().withProperty(AXIS, EnumFacing.byHorizontalIndex(random.nextInt(4))), 2);
        }
    }
}
