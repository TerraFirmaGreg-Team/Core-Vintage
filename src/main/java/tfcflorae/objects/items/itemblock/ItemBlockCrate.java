package tfcflorae.objects.items.itemblock;

import su.terrafirmagreg.modules.device.objects.blocks.BlockCrate;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

public class ItemBlockCrate extends ItemBlockTFC implements IItemSize {

    public ItemBlockCrate(BlockCrate block) {
        super(block);
    }
}
