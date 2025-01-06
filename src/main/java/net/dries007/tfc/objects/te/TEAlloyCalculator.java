package net.dries007.tfc.objects.te;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.util.Alloy;

import javax.annotation.Nullable;

public class TEAlloyCalculator extends TEInventory {

  private Alloy alloy;

  public TEAlloyCalculator() {
    super(9);

  }

  public Alloy getAlloy() {
    return this.alloy;
  }

  public void calculateAlloy() {
    Alloy computedAlloy = new Alloy();
    for (int slot = 0; slot < this.inventory.getSlots(); slot++) {
      computedAlloy.add(this.inventory.getStackInSlot(slot));
    }
    if (computedAlloy.getAmount() == 0) {
      this.alloy = null;
    } else {
      this.alloy = computedAlloy;
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setTag("Stacks", this.inventory.serializeNBT());
    return super.writeToNBT(nbt);
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    this.inventory.deserializeNBT(nbt.getCompoundTag("Stacks"));
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    if (!stack.hasCapability(CapabilityHeat.CAPABILITY, null)) {
      return false;
    }
    return CapabilityMetalItem.getMetalItem(stack) != null;
  }

  @Nullable
  @Override
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return (T) this.inventory;
    }

    return null;
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
  }
}
