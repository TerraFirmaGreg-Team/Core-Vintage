package tfcflorae.objects.items.itemblock;

import su.terrafirmagreg.api.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.device.objects.blocks.BlockCrate;


import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

public class ItemBlockCrate extends ItemBlockTFC implements ICapabilitySize {

    public ItemBlockCrate(BlockCrate block) {
        super(block);
    }
}
