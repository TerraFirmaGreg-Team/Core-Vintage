package com.buuz135.hotornot.object.item;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemTFC;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ItemMetalTongsHead extends ItemTFC implements IMetalItem {

    private static final Map<Metal, ItemMetalTongsHead> TONGS_HEAD_MAP = new HashMap<>();

    private final Metal metal;

    public ItemMetalTongsHead(final Metal metal) {
        this.metal = metal;

        TONGS_HEAD_MAP.put(metal, this);
    }

    /**
     * Gets the Tongs Head item for the metal MUST BE A TOOL METAL
     *
     * @param metal Metal type to get
     * @return Item instance for the given metal type
     */
    public static ItemMetalTongsHead get(final Metal metal) {
        return TONGS_HEAD_MAP.get(metal);
    }

    @Override
    public Metal getMetal(final ItemStack itemStack) {
        return metal;
    }

    @Override
    public int getSmeltAmount(final ItemStack itemStack) {
        return 100;
    }

    @Override
    public @NotNull Size getSize(final ItemStack itemStack) {
        return Size.NORMAL;
    }

    @Override
    public @NotNull Weight getWeight(final ItemStack itemStack) {
        return Weight.MEDIUM;
    }
}
