package net.dries007.tfc.module.rock.objects.blocks.itemblock;

import net.dries007.tfc.module.core.api.capability.size.IItemSizeAndWeight;
import net.dries007.tfc.module.core.api.capability.size.Size;
import net.dries007.tfc.module.core.api.capability.size.Weight;
import net.dries007.tfc.module.rock.objects.blocks.BlockRockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemRockSlab extends ItemSlab implements IItemSizeAndWeight {

    public ItemRockSlab(BlockRockSlab.Half slab, BlockRockSlab.Half slab1, BlockRockSlab.Double doubleSlab) {
        super(slab, slab1, doubleSlab);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL; // if blocks fits in small vessels, this should too
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_LIGHT; // Double the stacksize of a block (or 64)
    }
}
