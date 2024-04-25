package com.eerussianguy.firmalife.items;

import net.minecraft.item.ItemStack;


import com.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemMisc;

import org.jetbrains.annotations.NotNull;

public class ItemRoastedCocoaBeans extends ItemMisc {

    public ItemRoastedCocoaBeans() {
        super(Size.SMALL, Weight.LIGHT);
    }

    @Override
    @NotNull
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ItemsFL.COCOA_POWDER);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }
}
