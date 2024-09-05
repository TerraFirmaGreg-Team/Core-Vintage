package su.terrafirmagreg.modules.metal.objects.tile;

import su.terrafirmagreg.modules.core.capabilities.temperature.ProviderTemperature;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierBase;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.features.ambiental.provider.IAmbientalTileProvider;
import su.terrafirmagreg.modules.metal.ConfigMetal;
import su.terrafirmagreg.modules.metal.objects.itemblock.ItemBlockMetalLamp;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;


import net.dries007.tfc.api.capability.fluid.FluidHandlerSided;
import net.dries007.tfc.api.capability.fluid.FluidTankCallback;
import net.dries007.tfc.api.capability.fluid.FluidWhitelistHandlerComplex;
import net.dries007.tfc.api.capability.fluid.IFluidHandlerSidedCallback;
import net.dries007.tfc.api.capability.fluid.IFluidTankCallback;
import net.dries007.tfc.objects.te.TETickCounter;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class TileMetalLamp
    extends TETickCounter
    implements IFluidTankCallback, IFluidHandlerSidedCallback, IAmbientalTileProvider {

  public static int CAPACITY;
  private final FluidTank tank = new FluidTankCallback(this, 0, CAPACITY);

  @Setter
  @Getter
  private boolean powered = false;

  public TileMetalLamp() {
    CAPACITY = ConfigMetal.BLOCKS.LAMP.tank;
    this.tank.setCapacity(CAPACITY);
    this.tank.setTileEntity(this);
  }

  public int getFuel() {
    return tank.getFluidAmount();
  }

  @Override
  public void setAndUpdateFluidTank(int fluidTankID) {
    markForBlockUpdate();
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
      return (T) new FluidHandlerSided(this, tank, facing);
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public boolean canFill(FluidStack resource, EnumFacing side) {
    if (resource == null) {
      return false;
    }
    return ItemBlockMetalLamp.getValidFluids().contains(resource.getFluid());
  }

  @Override
  public boolean canDrain(EnumFacing side) {
    return true;
  }

  public ItemStack getItemStack(TileMetalLamp tile, IBlockState state) {
    ItemStack stack = new ItemStack(state.getBlock());
    IFluidHandlerItem itemCap = stack.getCapability(
        CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    IFluidHandler teCap = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    if (itemCap != null && teCap != null) {
      itemCap.fill(teCap.drain(CAPACITY, false), true); //don't drain creative item
    }
    return stack;
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    tank.readFromNBT(nbt.getCompoundTag("tank"));
    if (tank.getFluidAmount() > tank.getCapacity()) {
      // Fix config changes
      FluidStack fluidStack = tank.getFluid();
      if (fluidStack != null) {
        fluidStack.amount = tank.getCapacity();
      }
      tank.setFluid(fluidStack);
    }
    markForSync();
    powered = nbt.getBoolean("powered");
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
    nbt.setBoolean("powered", powered);
    return super.writeToNBT(nbt);
  }

  /**
   * Load up fluid handler contents from a lamps's ItemStack (after TEBarrel#loadFromItemStack)
   *
   * @param stack the lamp's stack to load contents from
   */
  public void loadFromItemStack(ItemStack stack) {
    IFluidHandler lampCap = stack.getCapability(
        CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (lampCap instanceof FluidWhitelistHandlerComplex) {
      NBTTagCompound contents = stack.getTagCompound();
      if (contents != null) {
        tank.fill(((FluidWhitelistHandlerComplex) lampCap).getFluid(), true);
        markForSync();
      }
    }
  }

  @Override
  public Optional<ModifierBase> getModifier(EntityPlayer player, TileEntity tile) {
    if (ModifierEnvironmental.getEnvironmentTemperature(player) < ProviderTemperature.AVERAGE) {
      float change = (this.isPowered() && this.getFuel() > 0) ? 1f : 0f;
      float potency = 0f;
      return ModifierBase.defined(this.getBlockType().getRegistryName().getPath(), change, potency);
    } else {
      return ModifierBase.none();
    }
  }
}
