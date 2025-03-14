package net.dries007.tfc.objects.items.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;

import javax.annotation.Nullable;

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
    return new CapabilityProviderHeat(nbt, heatCapacity, meltingPoint);
  }
}
