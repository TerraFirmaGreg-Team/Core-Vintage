package su.terrafirmagreg.modules.soil.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Block;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeat;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeatGrass;

import java.util.Map;

public final class BlocksSoil {

	public static final Map<Pair<SoilBlockVariant, SoilType>, Block> SOIL_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();

	public static BlockPeatGrass PEAT_GRASS;
	public static BlockPeat PEAT;

	public static void onRegister(RegistryManager registry) {

		registry.registerBlocks(SOIL_BLOCKS.values());

		PEAT_GRASS = registry.registerBlock(new BlockPeatGrass());
		PEAT = registry.registerBlock(new BlockPeat());


	}
}
