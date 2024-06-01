package tfcflorae.objects.items.groundcover;

import net.minecraft.item.ItemStack;


import mcp.MethodsReturnNonnullByDefault;


import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import tfcflorae.objects.blocks.groundcover.BlockPinecone;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class ItemPinecone extends ItemBlockTFC {

    public ItemPinecone(BlockPinecone block) {
        super(block);
        OreDictionaryHelper.register(this, "pinecone");
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
