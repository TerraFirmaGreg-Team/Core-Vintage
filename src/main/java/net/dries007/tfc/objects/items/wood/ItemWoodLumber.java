package net.dries007.tfc.objects.items.wood;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.wood.IWoodItem;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemWoodLumber extends ItemTFC implements IWoodItem {

    private final WoodType woodType;

    public ItemWoodLumber(WoodType woodType) {
        this.woodType = woodType;

        setMaxDamage(0);
        OreDictionaryHelper.register(this, "lumber");
        OreDictionaryHelper.register(this, "lumber", woodType.toString());
    }


    @Nonnull
    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_LIGHT;
    }


}
