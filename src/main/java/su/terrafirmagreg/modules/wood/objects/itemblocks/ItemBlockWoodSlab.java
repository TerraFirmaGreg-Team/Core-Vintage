package su.terrafirmagreg.modules.wood.objects.itemblocks;

import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodSlab;

public class ItemBlockWoodSlab extends ItemSlab implements IItemSize {

    public ItemBlockWoodSlab(BlockWoodSlab.Half slab, BlockWoodSlab.Half slab1, BlockWoodSlab.Double doubleSlab) {
        super(slab, slab1, doubleSlab);
    }

    @NotNull
    @Override
    public Size getSize(@NotNull ItemStack stack) {
        return Size.SMALL;
    }

    @NotNull
    @Override
    public Weight getWeight(@NotNull ItemStack stack) {
        return Weight.VERY_LIGHT;
    }
}
