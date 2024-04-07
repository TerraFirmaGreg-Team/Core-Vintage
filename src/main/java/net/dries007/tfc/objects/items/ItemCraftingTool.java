package net.dries007.tfc.objects.items;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;

public class ItemCraftingTool extends ItemMisc {

    public ItemCraftingTool(int durability, Size size, Weight weight, Object... oreNameParts) {
        super(size, weight, oreNameParts);
        setMaxDamage(durability);
        setMaxStackSize(1);
        setNoRepair();
    }

    @Override
    public boolean canStack(@NotNull ItemStack stack) {
        return false;
    }
}
