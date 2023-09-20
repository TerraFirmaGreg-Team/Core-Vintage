package net.dries007.tfc.module.core.common.objects.items.itemblocks;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.ItemSizeHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.module.core.common.objects.items.TFCItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemBlockTFC extends ItemBlock implements IItemSize {
    private final IItemSize size;

    public ItemBlockTFC(Block block) {
        this(block, block instanceof IItemSize ? (IItemSize) block : ItemSizeHandler.getDefault());
    }

    public ItemBlockTFC(Block block, IItemSize size) {
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
     * @see TFCItem#getItemStackLimit(ItemStack)
     */
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getWeight(stack).getStackSize();
    }
}
