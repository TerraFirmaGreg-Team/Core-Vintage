package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.WorldGenSettings;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;
import java.util.function.ToIntFunction;

public final class WorldGenRarityBased implements IWorldGenerator {
	private final ToIntFunction<WorldGenSettings> getRarityFunction;
	private final IWorldGenerator worldGenerator;

	public WorldGenRarityBased(ToIntFunction<WorldGenSettings> getRarityFunction, IWorldGenerator worldGenerator) {
		this.getRarityFunction = getRarityFunction;
		this.worldGenerator = worldGenerator;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (chunkGenerator instanceof ChunkGenTFC) {
			int rarity = getRarityFunction.applyAsInt(((ChunkGenTFC) chunkGenerator).s);
			if (rarity != 0 && random.nextInt(rarity) == 0)
				worldGenerator.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
}
