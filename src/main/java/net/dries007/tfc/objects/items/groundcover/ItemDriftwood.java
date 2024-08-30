package net.dries007.tfc.objects.items.groundcover;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.blocks.groundcover.BlockDriftwood;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class ItemDriftwood extends ItemBlockTFC {

    public ItemDriftwood(BlockDriftwood block) {
        super(block);
        OreDictionaryHelper.register(this, "wood");
        OreDictionaryHelper.register(this, "wood_driftwood");
        OreDictionaryHelper.register(this, "driftwood");
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.SMALL;
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.LIGHT;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
    }
}
