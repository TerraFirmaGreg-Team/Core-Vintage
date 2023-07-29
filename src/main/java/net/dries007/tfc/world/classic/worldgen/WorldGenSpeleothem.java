package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.util.IRockTypeBlock;
import net.dries007.tfc.objects.blocks.rock.BlockRockSpeleothem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static net.dries007.tfc.api.types2.rock.RockBlockType.ORDINARY;
import static net.dries007.tfc.api.types2.rock.RockVariant.RAW;
import static net.dries007.tfc.api.types2.rock.RockVariant.SPELEOTHEM;
import static net.dries007.tfc.objects.blocks.rock.BlockRock.getBlockRockMap;


public class WorldGenSpeleothem implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		// Вычисляем координаты центра чанка
		int x = chunkX * 16 + 8;
		int z = chunkZ * 16 + 8;

		int spread = 10; // Разброс для генерации кластеров сталактитов
		int tries = 60; // Количество попыток генерации кластеров сталактитов
		int innerSpread = 6; // Разброс для генерации дополнительных сталактитов внутри кластера
		int innerTries = 12; // Количество попыток генерации дополнительных сталактитов внутри кластера
		int upperBound = 256; // Максимальная высота генерации сталактитов
		int offset = 6; // Смещение по вертикали для начала генерации сталактитов

		for (int i = 0; i < tries; i++) {
			// Генерируем случайную позицию в пределах разброса и устанавливаем высоту в пределах верхней границы
			BlockPos target = new BlockPos(x + random.nextInt(spread), random.nextInt(upperBound) + offset, z + random.nextInt(spread));
			if (placeSpeleothemCluster(random, world, target, innerSpread, innerTries))
				i++;
		}
	}

	private boolean placeSpeleothemCluster(Random random, World world, BlockPos pos, int spread, int tries) {
		if (!findAndPlaceSpeleothem(random, world, pos))
			return false;

		for (int i = 0; i < tries; i++) {
			// Генерируем случайную позицию внутри кластера
			BlockPos target = pos.add(random.nextInt(spread * 2 + 1) - spread, random.nextInt(spread + 1) - spread, random.nextInt(spread * 2 + 1) - spread);
			findAndPlaceSpeleothem(random, world, target);
		}

		return true;
	}

	private boolean findAndPlaceSpeleothem(Random random, World world, BlockPos pos) {
		// Проверяем, является ли блок на позиции занятым. Если блок не воздух, возвращаем false.
		if (!world.isAirBlock(pos))
			return false;

		int off = world.provider.isNether() ? -1000 : 0; // Определяем смещение по вертикали. Если мир является "Nether", устанавливаем смещение в -1000, иначе 0.
		boolean up = random.nextBoolean(); // Генерируем случайное булево значение для определения направления (вверх или вниз).
		EnumFacing diff = (up ? EnumFacing.UP : EnumFacing.DOWN); // Определяем направление движения в зависимости от значения переменной "up".

		// Если направление движения вниз и блок виден небу, возвращаем false.
		if (!up && world.canBlockSeeSky(pos))
			return false;

		// Если текущая высота (Y-координата) больше 140, устанавливаем направление движения вниз (up = false).
		if (pos.getY() > 140)
			up = true;

		IBlockState stateAt;
		do {
			// Смещаем позицию вверх или вниз, пока не достигнем блока или границы высоты
			pos = pos.offset(diff);
			stateAt = world.getBlockState(pos);
			off++;
		} while (pos.getY() > 4 && pos.getY() < 200 && !stateAt.isFullBlock() && off < 10);

		Block type = getSpeleothemType(stateAt);
		placeSpeleothem(random, world, pos, type, !up);

		return true;
	}

	private void placeSpeleothem(Random random, World world, BlockPos pos, Block block, boolean up) {
		if (block == null)
			return;

		EnumFacing diff = up ? EnumFacing.UP : EnumFacing.DOWN;
		int size = random.nextInt(3) == 0 ? 2 : 3;
		if (!up && random.nextInt(20) == 0)
			size = 1;

		for (int i = 0; i < size; i++) {
			// Смещаем позицию вверх или вниз и устанавливаем сталактиты
			pos = pos.offset(diff);
			if (!world.isAirBlock(pos))
				return;
			if (block instanceof IRockTypeBlock) {
				BlockRockSpeleothem.EnumSize sizeType = BlockRockSpeleothem.EnumSize.values()[size - i - 1];
				// Создаем блок сталактита с указанным размером и типом породы
				IBlockState targetBlock = getBlockRockMap(ORDINARY, SPELEOTHEM, ((IRockTypeBlock) block).getRockType()).getDefaultState().withProperty(BlockRockSpeleothem.SIZE, sizeType);
				// Устанавливаем блок сталактита в мир
				world.setBlockState(pos, targetBlock);
			}
		}
	}

	@SuppressWarnings("incomplete-switch")
	private Block getSpeleothemType(IBlockState state) {
		Block block = state.getBlock();
		for (RockType rockType : RockType.values()) {
			if (getBlockRockMap(ORDINARY, RAW, rockType) == block) {
				return getBlockRockMap(ORDINARY, RAW, rockType);
			}
		}

		return null;
	}
}
