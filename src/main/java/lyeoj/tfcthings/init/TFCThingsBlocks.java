package lyeoj.tfcthings.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;


import lyeoj.tfcthings.blocks.BlockRopeBridge;
import lyeoj.tfcthings.blocks.BlockRopeLadder;

public class TFCThingsBlocks {

    public static final Block ROPE_BRIDGE_BLOCK = new BlockRopeBridge();
    public static final Item ROPE_BRIDGE_ITEM = new ItemBlock(ROPE_BRIDGE_BLOCK).setRegistryName(ROPE_BRIDGE_BLOCK.getRegistryName());
    public static final Block ROPE_LADDER_BLOCK = new BlockRopeLadder();
    public static final Item ROPE_LADDER_ITEM = new ItemBlock(ROPE_LADDER_BLOCK).setRegistryName(ROPE_LADDER_BLOCK.getRegistryName());

    public static final Block[] BLOCKLIST = {

            ROPE_BRIDGE_BLOCK,
            ROPE_LADDER_BLOCK
    };
}
