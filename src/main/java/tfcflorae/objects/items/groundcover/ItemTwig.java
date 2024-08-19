package tfcflorae.objects.items.groundcover;

import net.minecraft.item.ItemStack;


import mcp.MethodsReturnNonnullByDefault;


import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;


import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import tfcflorae.objects.blocks.groundcover.BlockTwig;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class ItemTwig extends ItemBlockTFC {

    public ItemTwig(BlockTwig block) {
        super(block);
        OreDictionaryHelper.register(this, "wood");
        OreDictionaryHelper.register(this, "wood_twig");
        OreDictionaryHelper.register(this, "twig");
        OreDictionaryHelper.register(this, "wood_stick");
        OreDictionaryHelper.register(this, "stick");
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
