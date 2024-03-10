package su.terrafirmagreg.modules.soil.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeat;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeatGrass;

import java.util.Map;

import static su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants.*;

public final class BlocksSoil {

	public static final Map<Pair<SoilBlockVariant, SoilType>, Block> SOIL_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

	public static BlockPeatGrass PEAT_GRASS;
	public static BlockPeat PEAT;

	public static void onRegister(RegistryManager registry) {

		for (var block : SOIL_BLOCKS.values()) registry.registerAuto(block);

		registry.registerAuto(PEAT_GRASS = new BlockPeatGrass());
		registry.registerAuto(PEAT = new BlockPeat());


	}

	public static boolean isGrass(IBlockState current) {
		if (current.getBlock() instanceof BlockPeatGrass) return true;
		if (current.getBlock() instanceof ISoilBlock soil) {
			var variant = soil.getBlockVariant();
			return variant == GRASS || variant == DRY_GRASS ||
					variant == CLAY_GRASS;
		}
		return false;
	}

	public static boolean isSoil(IBlockState current) {
		if (current.getBlock() instanceof BlockPeat) return true;
		if (current.getBlock() instanceof ISoilBlock soil) {
			var variant = soil.getBlockVariant();
			return variant == GRASS || variant == DRY_GRASS ||
					variant == DIRT || variant == CLAY ||
					variant == CLAY_GRASS;
		}
		return false;
	}
}
