package net.dries007.tfc.objects.items;

import su.terrafirmagreg.api.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

public class ItemMisc extends ItemTFC implements ICapabilitySize {

    private final Size size;
    private final Weight weight;

    public ItemMisc(Size size, Weight weight, Object... oreNameParts) {
        this(size, weight);

        for (Object obj : oreNameParts) {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
    }

    public ItemMisc(Size size, Weight weight) {
        this.size = size;
        this.weight = weight;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return size;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return weight;
    }
}
