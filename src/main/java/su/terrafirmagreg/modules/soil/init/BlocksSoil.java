package su.terrafirmagreg.modules.soil.init;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.api.util.Pair;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlockVariant;
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


	@NotNull
	public static Block getBlock(@NotNull SoilBlockVariant variant, @NotNull SoilType type) {
		var block = (Block) SOIL_BLOCKS.get(new Pair<>(variant, type));
		if (block != null) return block;
		throw new RuntimeException(String.format("Block soil is null: %s, %s", variant, type));
	}

	public static boolean isGrass(IBlockState current) {
		if (current.getBlock() instanceof BlockPeatGrass) return true;
		if (current.getBlock() instanceof ISoilBlockVariant soil) {
			var variant = soil.getBlockVariant();
			return variant == GRASS || variant == DRY_GRASS ||
					variant == CLAY_GRASS;
		}
		return false;
	}

	public static boolean isSoil(IBlockState current) {
		if (current.getBlock() instanceof BlockPeat) return true;
		if (current.getBlock() instanceof ISoilBlockVariant soil) {
			var variant = soil.getBlockVariant();
			return variant == GRASS || variant == DRY_GRASS ||
					variant == DIRT || variant == CLAY ||
					variant == CLAY_GRASS;
		}
		return false;
	}
}
