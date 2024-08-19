package net.dries007.tfc.objects.items.itemblock;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.ProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;


import org.jetbrains.annotations.NotNull;

public class ItemBlockTFC extends ItemBlock implements ICapabilitySize {

    private final ICapabilitySize size;

    public ItemBlockTFC(Block block) {
        this(block, block instanceof ICapabilitySize itemSize ? itemSize : ProviderSize.getDefault());
    }

    public ItemBlockTFC(Block block, ICapabilitySize size) {
        super(block);

        this.size = size;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return size.getSize(stack);
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return size.getWeight(stack);
    }

    @Override
    public boolean canStack(@NotNull ItemStack stack) {
        return size.canStack(stack);
    }

    /**
     * @see net.dries007.tfc.objects.items.ItemTFC#getItemStackLimit(ItemStack)
     */
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getWeight(stack).stackSize;
    }
}
