package net.dries007.tfc.api.capability;

import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * This is an interface for the capability that is returned by a Small Vessel. You can safely cast it to this.
 */
public interface ISmallVesselHandler extends IItemHandlerModifiable, IFluidHandler, INBTSerializable<NBTTagCompound>, ICapabilityHeat, IMoldHandler {

  /**
   * This gets the fluid mode, including liquid temperature status
   *
   * @return INVENTORY = items, LIQUID = fluids molten or solid based on temperature
   */
  Mode getFluidMode();

  /**
   * This sets the fluid mode. When fluid is empty, it defaults to item mode
   *
   * @param fluidMode true = fluids, false = items
   */
  void setFluidMode(boolean fluidMode);

  enum Mode {
    INVENTORY,
    LIQUID_MOLTEN,
    LIQUID_SOLID
  }

}
