package net.dries007.tfc.world.classic.genlayers.datalayers.rock;

import net.dries007.tfc.api.types2.rock.RockCategory;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class GenLayerRockInit extends GenLayerTFC {
	private final int[] layerRocks;

	public GenLayerRockInit(long par1, final RockCategory.Layer rocks) {
		super(par1);

		layerRocks = Arrays.stream(RockType.values())
				.filter(rocks::test)
				.mapToInt(RockType::ordinal)
				.sorted()
				.toArray();
	}

	@Override
	@Nonnull
	public int[] getInts(int par1, int par2, int maxX, int maxZ) {
		int[] cache = IntCache.getIntCache(maxX * maxZ);

		for (int z = 0; z < maxZ; ++z) {
			for (int x = 0; x < maxX; ++x) {
				this.initChunkSeed(par1 + x, par2 + z);
				cache[x + z * maxX] = layerRocks[this.nextInt(layerRocks.length)];
			}
		}

		return cache;
	}
}
