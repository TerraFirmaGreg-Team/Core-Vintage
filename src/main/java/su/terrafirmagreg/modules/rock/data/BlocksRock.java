package su.terrafirmagreg.modules.rock.data;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockAlabasterBricks;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockAlabasterRaw;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockAlabasterSmooth;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockRockDecorative;

import java.util.Map;

public final class BlocksRock {

	public static final Map<Pair<RockBlockVariant, RockType>, Block> ROCK_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();
	public static final Map<Pair<RockBlockVariant, EnumDyeColor>, BlockRockDecorative> ALABASTER_COLOR_BLOCKS = new Object2ObjectLinkedOpenHashMap<>();


	public static BlockAlabasterBricks ALABASTER_BRICKS;
	public static BlockAlabasterSmooth ALABASTER_SMOOTH;
	public static BlockAlabasterRaw ALABASTER_RAW;

	public static void onRegister(RegistryManager registry) {

		registry.registerBlocks(ROCK_BLOCKS.values());

		ALABASTER_BRICKS = registry.registerBlock(new BlockAlabasterBricks());
		ALABASTER_SMOOTH = registry.registerBlock(new BlockAlabasterSmooth());
		ALABASTER_RAW = registry.registerBlock(new BlockAlabasterRaw());

		registry.registerBlocks(ALABASTER_COLOR_BLOCKS.values());
	}


}
