package com.buuz135.hotornot.object.item;

import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.types.Metal;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ItemMetalTongs extends ItemHotHolder implements ICapabilityMetal {

    private static final Map<Metal, ItemMetalTongs> TONGS_MAP = new HashMap<>();
    private final Metal metal;

    /**
     * @param metal The Metal type these tongs are
     */
    public ItemMetalTongs(final Metal metal) {
        super(metal.getTier());

        // Go kaboom if metal isn't for tools
        if (!metal.isToolMetal()) throw new IllegalArgumentException("You can't make tongs out of non tool metals.");

        this.metal = metal;

        TONGS_MAP.put(metal, this);

        //noinspection DataFlowIssue
        setMaxDamage(metal.getToolMetal().getMaxUses() * 2);
    }

    /**
     * Gets the Tongs item for the metal MUST BE A TOOL METAL
     *
     * @param metal Metal type to get
     * @return Item instance for the given metal type
     */
    public static ItemMetalTongs get(final Metal metal) {
        return TONGS_MAP.get(metal);
    }

    @Override
    public Metal getMetal(final ItemStack itemStack) {
        return metal;
    }

    @Override
    public int getSmeltAmount(final ItemStack itemStack) {
        // They are worth 200 units
        if (!isDamageable() || !itemStack.isItemDamaged()) return 200;

        final double damagePercent = (double) (itemStack.getMaxDamage() - itemStack.getItemDamage()) / itemStack.getMaxDamage() - 0.1D;
        return damagePercent < 0 ? 0 : MathHelper.floor(200 * damagePercent);
    }

    @Override
    public boolean canMelt(final ItemStack stack) {
        return true;
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack itemStack, @Nullable final NBTTagCompound nbt) {
        return new ForgeableHeatableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
    }
}
