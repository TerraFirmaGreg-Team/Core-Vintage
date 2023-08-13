package net.dries007.tfc.common.objects.items.wood;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodDoor;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemWoodDoor extends ItemDoor implements IItemSize {

    public final WoodType woodType;

    public ItemWoodDoor(BlockWoodDoor blockWoodDoor) {
        super(blockWoodDoor);

        woodType = blockWoodDoor.getWoodType();
        OreDictionaryHelper.register(this, "door", "wood");
        //noinspection ConstantConditions
        OreDictionaryHelper.register(this, "door", "wood", woodType.toString());
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack) {
        return Size.VERY_LARGE; // Can't be stored
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.HEAVY; // Stacksize = 4
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
    }
}
