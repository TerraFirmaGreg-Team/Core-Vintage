package net.dries007.tfc.objects.items;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.blocks.wood.BlockWoodSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemSlabTFC extends ItemSlab implements IItemSize {
    public ItemSlabTFC(BlockWoodSlab.Half slab, BlockWoodSlab.Half slab1, BlockWoodSlab.Double doubleSlab) {
        super(slab, slab1, doubleSlab);
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack) {
        return Size.SMALL; // if blocks fits in small vessels, this should too
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.VERY_LIGHT; // Double the stacksize of a block (or 64)
    }
}
