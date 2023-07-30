/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static net.dries007.tfc.api.types2.rock.RockBlockType.ORDINARY;
import static net.dries007.tfc.api.types2.rock.RockVariant.LOOSE;

public class WorldGenLooseRocks implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
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
				generateRock(world, pos.up(world.getTopSolidOrLiquidBlock(pos).getY()), rock);
			}
		}
	}

	protected void generateRock(World world, BlockPos pos, RockType rockType) {
		// Используем воздух, чтобы не заменять другие генерируемые блоки
		// Это соответствует проверке в BlockPlacedItemFlat, если блок может оставаться
		// Также добавляем только на почву, так как это вызывается обработчиком регенерации мира позже
		if (world.isAirBlock(pos) &&
				world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) &&
				BlocksTFC.isSoil(world.getBlockState(pos.down()))) {
			if (ConfigTFC.General.WORLD.enableLooseRocks) {
				world.setBlockState(pos, TFCStorage.getRockBlock(ORDINARY, LOOSE, rockType).getDefaultState(), 2);
			}
		}
	}
}
