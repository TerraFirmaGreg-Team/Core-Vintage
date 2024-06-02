package net.dries007.tfc.objects.items.itemblock;

import su.terrafirmagreg.api.capabilities.heat.ProviderHeat;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import org.jetbrains.annotations.Nullable;

public class ItemBlockHeat extends ItemBlockTFC {

    private final float heatCapacity;
    private final float meltingPoint;

    public ItemBlockHeat(Block block, float heatCapacity, float meltingPoint) {
        super(block);

        this.heatCapacity = heatCapacity;
        this.meltingPoint = meltingPoint;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ProviderHeat(nbt, heatCapacity, meltingPoint);
    }
}
