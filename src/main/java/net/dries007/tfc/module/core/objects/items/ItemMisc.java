package net.dries007.tfc.module.core.objects.items;

import net.dries007.tfc.module.core.api.capability.size.IItemSizeAndWeight;
import net.dries007.tfc.module.core.api.capability.size.Size;
import net.dries007.tfc.module.core.api.capability.size.Weight;
import net.dries007.tfc.module.core.api.objects.item.ItemBase;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;


public class ItemMisc extends ItemBase implements IItemSizeAndWeight {
    private final Size size;
    private final Weight weight;

    public ItemMisc(Size size, Weight weight, String... oreNameParts) {
        this(size, weight);

        for (String oreName : oreNameParts) {
            OreDictionaryHelper.register(this, oreName);
        }
    }

    public ItemMisc(Size size, Weight weight) {

        this.size = size;
        this.weight = weight;
    }


    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return size;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return weight;
    }
}
