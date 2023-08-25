package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.api.types.rock.variant.RockBlockVariants.SAND;

@ParametersAreNonnullByDefault
public class WorldGenSand extends WorldGenerator {
	private final int radius;

	public WorldGenSand(int radius) {
		this.radius = radius;
	}

	@Override
	public boolean generate(World world, Random rng, BlockPos pos) {
		if (TFCBlocks.isWater(world.getBlockState(pos))) return false;

		final Block sand = TFCBlocks.getRockBlock(SAND, ChunkDataTFC.getRock1(world, pos));
		final int rnd = rng.nextInt(this.radius - 2) + 2;

		for (int x = -rnd; x <= rnd; x++) {
			for (int z = -rnd; z <= rnd; z++) {
				if (x * x + z * z > rnd * rnd) continue;
				for (int y = -2; y <= 2; y++) {
					final IBlockState s = world.getBlockState(pos.add(x, y, z));
					if (TFCBlocks.isSoil(s) || TFCBlocks.isSand(s))
						world.setBlockState(pos.add(x, y, z), sand.getDefaultState(), 2);
				}
			}
		}

		return true;
	}
}
