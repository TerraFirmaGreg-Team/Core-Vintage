package net.dries007.tfc.objects.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("WeakerAccess")
public class ItemMiscHeatable extends ItemMisc {

    private final float heatCapacity;
    private final float meltTemp;

    public ItemMiscHeatable(Size size, Weight weight, float heatCapacity, float meltTemp) {
        super(size, weight);
        this.heatCapacity = heatCapacity;
        this.meltTemp = meltTemp;
    }

    public ItemMiscHeatable(Size size, Weight weight, float heatCapacity, float meltTemp, String oreDictionary) {
        super(size, weight, oreDictionary);
        this.heatCapacity = heatCapacity;
        this.meltTemp = meltTemp;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ItemHeatHandler(nbt, heatCapacity, meltTemp);
    }
}
