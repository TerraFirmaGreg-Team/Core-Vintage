package su.terrafirmagreg.tfg.modules.core.api.block.itemblocks;


import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.IItemSize;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.ItemSizeHandler;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.Size;
import su.terrafirmagreg.tfg.modules.core.api.capability.size.Weight;
import su.terrafirmagreg.tfg.modules.core.api.item.ItemBase;

import javax.annotation.Nonnull;

public class ItemBlockBase extends ItemBlock implements IItemSize {
    private final IItemSize size;

    public ItemBlockBase(Block block) {
        this(block, block instanceof IItemSize ? (IItemSize) block : ItemSizeHandler.getDefault());
    }

    public ItemBlockBase(Block block, IItemSize size) {
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
    public int getItemStackLimit(ItemStack stack) {
        return getWeight(stack).getStackSize();
    }
}
