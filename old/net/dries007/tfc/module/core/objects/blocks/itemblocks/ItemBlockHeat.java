package net.dries007.tfc.module.core.objects.blocks.itemblocks;

import net.dries007.tfc.module.core.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.module.core.api.objects.block.itemblocks.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class ItemBlockHeat extends ItemBlockBase {
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
        return new ItemHeatHandler(nbt, heatCapacity, meltingPoint);
    }
}
