package net.dries007.tfctech.objects.storage;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import gregtech.api.capability.IEnergyContainer;
import net.dries007.tfctech.TechConfig;

/**
 * Energy storage for machines Energy can only be input from outside and only be extracted internally
 */
@SuppressWarnings("WeakerAccess")
public class MachineEnergyContainer extends EnergyStorage implements INBTSerializable<NBTTagCompound> {

  private final GTCEHandler gtceHandler = new GTCEHandler(this);

  public MachineEnergyContainer(@Nullable NBTTagCompound nbt) {
    this(10000, 10000, 0);
    deserializeNBT(nbt);
  }

  public MachineEnergyContainer(int capacity, int maxReceive, int energy) {
    super(capacity, maxReceive, 0, energy);
  }

  /**
   * Tries to consume energy from storage.
   *
   * @param amount   the amount to consume
   * @param simulate if this is a simulation, the energy isn't really consumed
   * @return return true if the amount could be consumed
   */
  public boolean consumeEnergy(int amount, boolean simulate) {
    if (amount > energy) {return false;}
    if (!simulate) {energy -= amount;}
    return true;
  }

  /**
   * Use only in client! this is here to update internal energy for GUI purposes
   *
   * @param amount the value to set the internal energy to
   */
  @SideOnly(Side.CLIENT)
  public void setEnergy(int amount) {
    energy = amount;
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setInteger("energy", energy);
    return nbt;
  }

  @Override
  public void deserializeNBT(@Nullable NBTTagCompound nbt) {
    if (nbt != null) {
      energy = nbt.getInteger("energy");
    }
  }

  public GTCEHandler getGTCEHandler() {
    return gtceHandler;
  }

  @Optional.Interface(iface = "gregtech.api.capability.IEnergyContainer", modid = "gregtech")
  public static class GTCEHandler implements IEnergyContainer {

    private final MachineEnergyContainer container;

    public GTCEHandler(MachineEnergyContainer mainContainer) {
      container = mainContainer;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
      long canAccept = getEnergyCapacity() - getEnergyStored();
      if (voltage > 0L && amperage > 0L) {
        if (canAccept >= voltage) {
          long amperesAccepted = Math.min(canAccept / voltage, Math.min(amperage, getInputAmperage()));
          if (amperesAccepted > 0) {
            setEnergyStored(getEnergyStored() + voltage * amperesAccepted);
            return amperesAccepted;
          }
        }
      }
      return 0;
    }

    @Override
    public boolean inputsEnergy(EnumFacing enumFacing) {
      return true;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
      long oldEnergyStored = getEnergyStored();
      long newEnergyStored = (getEnergyCapacity() - getEnergyStored() < energyToAdd) ? getEnergyCapacity() : (oldEnergyStored + energyToAdd);
      if (newEnergyStored < 0) {newEnergyStored = 0;}
      setEnergyStored(newEnergyStored);
      return newEnergyStored - oldEnergyStored;
    }

    @Override
    public long getEnergyStored() {
      return (long) Math.floor(this.container.getEnergyStored() / (double) TechConfig.DEVICES.ratioGTCE);
    }

    public void setEnergyStored(long energyStored) {
      this.container.setEnergy((int) (energyStored * TechConfig.DEVICES.ratioGTCE));
    }

    @Override
    public long getEnergyCapacity() {
      return (long) Math.ceil(this.container.getMaxEnergyStored() / (double) TechConfig.DEVICES.ratioGTCE);
    }

    @Override
    public long getInputAmperage() {
      return 1L;
    }

    @Override
    public long getInputVoltage() {
      return TechConfig.DEVICES.gtceVoltage;
    }

    @Override
    public boolean isOneProbeHidden() {
      return true;
    }
  }
}
