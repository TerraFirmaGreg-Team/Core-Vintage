package net.dries007.tfc.module.core.api.objects.block.itemblocks;

import net.dries007.tfc.module.core.api.capability.size.IItemSizeAndWeight;
import net.dries007.tfc.module.core.api.capability.size.ItemSizeHandler;
import net.dries007.tfc.module.core.api.capability.size.Size;
import net.dries007.tfc.module.core.api.capability.size.Weight;
import net.dries007.tfc.module.core.api.objects.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemBlockBase extends ItemBlock implements IItemSizeAndWeight {
    private final IItemSizeAndWeight size;

    public ItemBlockBase(Block block) {
        this(block, block instanceof IItemSizeAndWeight ? (IItemSizeAndWeight) block : ItemSizeHandler.getDefault());
    }

    public ItemBlockBase(Block block, IItemSizeAndWeight size) {
        super(block);

        this.size = size;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return size.getSize(stack);
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return size.getWeight(stack);
    }

    @Override
    public boolean canStack(@Nonnull ItemStack stack) {
        return size.canStack(stack);
    }

    /**
     * @see ItemBase#getItemStackLimit(ItemStack)
     */
    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return getWeight(stack).getStackSize();
    }
}
