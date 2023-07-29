/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

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
				.filter(rock -> rocks.test(rock))
				.mapToInt(RockType::ordinal)
				.sorted()
				.toArray();
//		layerRocks = TFCRegistries.ROCKS.getValuesCollection()
//				.stream()
//				.filter(rocks)
//				.filter(Rock::isNaturallyGenerating)
//				.mapToInt(((ForgeRegistry<Rock>) TFCRegistries.ROCKS)::getID)
//				.sorted()
//				.toArray();

//		if (ConfigTFC.General.DEBUG.debugWorldGenSafe) {
//			TerraFirmaCraft.getLog().info("Список камней для генерации мира (целые числа): {}", layerRocks);
//			TerraFirmaCraft.getLog().info("Список камней для генерации мира (названия): {}", (Object) Arrays.stream(layerRocks)
//					.mapToObj(((ForgeRegistry<Rock>) TFCRegistries.ROCKS)::getValue)
//					.map(Objects::toString)
//					.toArray());
//		}
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
