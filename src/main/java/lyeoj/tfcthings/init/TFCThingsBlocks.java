package lyeoj.tfcthings.init;

import lyeoj.tfcthings.blocks.*;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.types.DefaultMetals;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class TFCThingsBlocks {

	public static final Block BEAR_TRAP = new BlockBearTrap();
	public static final Item BEAR_TRAP_ITEM = new ItemBlock(BEAR_TRAP).setRegistryName(BEAR_TRAP.getRegistryName())
			.setMaxStackSize(2);
	public static final Block PIGVIL_BLOCK = new BlockPigvil(TFCRegistries.METALS.getValue(DefaultMetals.STEEL));
	public static final Item PIGVIL_ITEM = new ItemBlock(PIGVIL_BLOCK).setRegistryName(PIGVIL_BLOCK.getRegistryName());
	public static final Block PIGVIL_BLOCK_BLACK = new BlockPigvil(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL));
	public static final Item PIGVIL_ITEM_BLACK = new ItemBlock(PIGVIL_BLOCK_BLACK).setRegistryName(PIGVIL_BLOCK_BLACK.getRegistryName());
	public static final Block PIGVIL_BLOCK_RED = new BlockPigvil(TFCRegistries.METALS.getValue(DefaultMetals.RED_STEEL));
	public static final Item PIGVIL_ITEM_RED = new ItemBlock(PIGVIL_BLOCK_RED).setRegistryName(PIGVIL_BLOCK_RED.getRegistryName());
	public static final Block PIGVIL_BLOCK_BLUE = new BlockPigvil(TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL));
	public static final Item PIGVIL_ITEM_BLUE = new ItemBlock(PIGVIL_BLOCK_BLUE).setRegistryName(PIGVIL_BLOCK_BLUE.getRegistryName());
	public static final Block PIGVIL_BLOCK_PURPLE = new BlockPigvil();
	public static final Item PIGVIL_ITEM_PURPLE = new ItemBlock(PIGVIL_BLOCK_PURPLE).setRegistryName(PIGVIL_BLOCK_PURPLE.getRegistryName());
	public static final Block SNARE_BLOCK = new BlockSnare();
	public static final Item SNARE_ITEM = new ItemBlock(SNARE_BLOCK).setRegistryName(SNARE_BLOCK.getRegistryName())
			.setMaxStackSize(4);
	public static final Block ROPE_BRIDGE_BLOCK = new BlockRopeBridge();
	public static final Item ROPE_BRIDGE_ITEM = new ItemBlock(ROPE_BRIDGE_BLOCK).setRegistryName(ROPE_BRIDGE_BLOCK.getRegistryName());
	public static final Block ROPE_LADDER_BLOCK = new BlockRopeLadder();
	public static final Item ROPE_LADDER_ITEM = new ItemBlock(ROPE_LADDER_BLOCK).setRegistryName(ROPE_LADDER_BLOCK.getRegistryName());
	public static final Block GRINDSTONE_BLOCK = new BlockGrindstone();
	public static final Item GRINDSTONE_ITEM = new ItemBlock(GRINDSTONE_BLOCK)
			.setRegistryName(GRINDSTONE_BLOCK.getRegistryName())
			.setMaxStackSize(4);
	public static final Block[] BLOCKLIST = {
			BEAR_TRAP,
			PIGVIL_BLOCK,
			PIGVIL_BLOCK_BLACK,
			PIGVIL_BLOCK_BLUE,
			PIGVIL_BLOCK_RED,
			PIGVIL_BLOCK_PURPLE,
			SNARE_BLOCK,
			ROPE_BRIDGE_BLOCK,
			ROPE_LADDER_BLOCK,
			GRINDSTONE_BLOCK
	};
}
