package pieman.caffeineaddon.items;

import su.terrafirmagreg.api.capabilities.heat.ProviderHeat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import org.jetbrains.annotations.Nullable;

public class ItemCoffeeHeatable extends ItemCoffee {

    public ItemCoffeeHeatable(String name) {
        super(name);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ProviderHeat(nbt, 1.0f, 1599f);
    }

}
