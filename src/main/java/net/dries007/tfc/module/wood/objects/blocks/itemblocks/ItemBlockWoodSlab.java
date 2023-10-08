package net.dries007.tfc.module.wood.objects.blocks.itemblocks;

import net.dries007.tfc.module.core.api.capability.size.IItemSizeAndWeight;
import net.dries007.tfc.module.core.api.capability.size.Size;
import net.dries007.tfc.module.core.api.capability.size.Weight;
import net.dries007.tfc.module.wood.objects.blocks.BlockWoodSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemBlockWoodSlab extends ItemSlab implements IItemSizeAndWeight {

    public ItemBlockWoodSlab(BlockWoodSlab.Half slab, BlockWoodSlab.Half slab1, BlockWoodSlab.Double doubleSlab) {
        super(slab, slab1, doubleSlab);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_LIGHT;
    }
}
