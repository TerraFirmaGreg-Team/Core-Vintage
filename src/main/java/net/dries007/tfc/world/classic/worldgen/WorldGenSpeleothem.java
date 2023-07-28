package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.api.types2.rock.RockType;
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
import static net.dries007.tfc.objects.blocks.rock.BlockRock.getBlockRockMap;


public class WorldGenSpeleothem implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		int x = chunkX * 16 + 8;
		int z = chunkZ * 16 + 8;

		int spread = 10;
		int tries = 60;
		int innerSpread = 6;
		int innerTries = 12;
		int upperBound = 55;
		int offset = 6;

		if (upperBound > 0)
			for (int i = 0; i < tries; i++) {
				BlockPos target = new BlockPos(x + random.nextInt(spread), random.nextInt(upperBound) + offset, z + random.nextInt(spread));
				if (placeSpeleothemCluster(random, world, target, innerSpread, innerTries))
					i++;
			}
	}

	private boolean placeSpeleothemCluster(Random random, World world, BlockPos pos, int spread, int tries) {
		if (!findAndPlaceSpeleothem(random, world, pos))
			return false;

		for (int i = 0; i < tries; i++) {
			BlockPos target = pos.add(random.nextInt(spread * 2 + 1) - spread, random.nextInt(spread + 1) - spread, random.nextInt(spread * 2 + 1) - spread);
			findAndPlaceSpeleothem(random, world, target);
		}

		return true;
	}

	private boolean findAndPlaceSpeleothem(Random random, World world, BlockPos pos) {
		if (!world.isAirBlock(pos))
			return false;

		int off = world.provider.isNether() ? -1000 : 0;
		boolean up = random.nextBoolean();
		EnumFacing diff = (up ? EnumFacing.UP : EnumFacing.DOWN);

		if (!up && world.canBlockSeeSky(pos))
			return false;

		IBlockState stateAt;
		do {
			pos = pos.offset(diff);
			stateAt = world.getBlockState(pos);
			off++;
		} while (pos.getY() > 4 && pos.getY() < 200 && !stateAt.isFullBlock() && off < 10);

		Block type = getSpeleothemType(stateAt);
		placeSpeleothem(random, world, pos, type, !up);

		return true;
	}

	private void placeSpeleothem(Random random, World world, BlockPos pos, Block type, boolean up) {
		if (type == null)
			return;

		EnumFacing diff = up ? EnumFacing.UP : EnumFacing.DOWN;
		int size = random.nextInt(3) == 0 ? 2 : 3;
		if (!up && random.nextInt(20) == 0)
			size = 1;

		for (int i = 0; i < size; i++) {
			pos = pos.offset(diff);
			if (!world.isAirBlock(pos))
				return;

			BlockRockSpeleothem.EnumSize sizeType = BlockRockSpeleothem.EnumSize.values()[size - i - 1];
			IBlockState targetBlock = type.getDefaultState().withProperty(BlockRockSpeleothem.SIZE, sizeType);
			world.setBlockState(pos, targetBlock);
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
