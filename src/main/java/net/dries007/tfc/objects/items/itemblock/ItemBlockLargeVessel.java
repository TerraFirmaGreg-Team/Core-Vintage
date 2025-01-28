package net.dries007.tfc.objects.items.itemblock;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.objects.blocks.BlockLargeVessel;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemBlockLargeVessel extends ItemBlockTFC implements IItemSize {

  public ItemBlockLargeVessel(BlockLargeVessel block) {
    super(block);
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    // Since this is technically still a pottery item, despite being a block
    return new CapabilityProviderHeat(nbt, 1.0f, 1599f);
  }
}
