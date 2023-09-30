package net.dries007.tfc.module.core.objects.items;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemCraftingTool extends ItemMisc {
    public ItemCraftingTool(int durability, Size size, Weight weight, String... oreNameParts) {
        super(size, weight, oreNameParts);
        setMaxDamage(durability);
        setMaxStackSize(1);
        setNoRepair();
    }

    @Override
    public boolean canStack(@Nonnull ItemStack stack) {
        return false;
    }
}
