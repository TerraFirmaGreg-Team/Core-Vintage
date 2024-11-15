package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.tile.BaseTileTickableInventory;
import su.terrafirmagreg.api.base.tile.spi.ITileFields;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.feature.ambiental.modifiers.ModifierBase;
import su.terrafirmagreg.modules.core.feature.ambiental.modifiers.ModifierTile;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalTileProvider;
import su.terrafirmagreg.modules.device.client.audio.IMachineSoundEffect;
import su.terrafirmagreg.modules.device.client.gui.GuiElectricForge;
import su.terrafirmagreg.modules.device.init.SoundsDevice;
import su.terrafirmagreg.modules.device.object.block.BlockElectricForge;
import su.terrafirmagreg.modules.device.object.container.ContainerElectricForge;
import su.terrafirmagreg.modules.device.object.storage.MachineEnergyStorage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import gregtech.api.capability.GregtechCapabilities;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfctech.TFCTech;
import net.dries007.tfctech.TechConfig;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

@SuppressWarnings("WeakerAccess")
//@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "ic2")
public class TileElectricForge extends BaseTileTickableInventory
  implements ITileFields, IMachineSoundEffect, IAmbientalTileProvider, IProviderContainer<ContainerElectricForge, GuiElectricForge> {

  public static final int SLOT_INPUT_MIN = 0;
  public static final int SLOT_INPUT_MAX = 8;
  public static final int SLOT_EXTRA_MIN = 9;
  public static final int SLOT_EXTRA_MAX = 11;
  private final HeatRecipe[] cachedRecipes = new HeatRecipe[9];
  private final MachineEnergyStorage energyContainer;
  private final boolean addedToIc2Network = false;
  private float targetTemperature = 0.0F;
  private int litTime = 0;
  private boolean soundPlay = false;

  public TileElectricForge() {
    super(12);

    this.energyContainer = new MachineEnergyStorage(TechConfig.DEVICES.electricForgeEnergyCapacity,
                                                    TechConfig.DEVICES.electricForgeEnergyCapacity, 0);
    Arrays.fill(cachedRecipes, null);
  }

  public void addTargetTemperature(int value) {
    targetTemperature += value;
    if (targetTemperature > (float) TechConfig.DEVICES.electricForgeMaxTemperature) {
      targetTemperature = (float) TechConfig.DEVICES.electricForgeMaxTemperature;
    }
    if (targetTemperature < 0) {
      targetTemperature = 0;
    }
  }

  //    @Optional.Method(modid = "ic2")
  //    @Override
  //    public void invalidate() {
  //        super.invalidate();
  //        if (!world.isRemote && addedToIc2Network) {
  //            MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileUnloadEvent(this));
  //            addedToIc2Network = false;
  //        }
  //    }
  //
  //    @Optional.Method(modid = "ic2")
  //    @Override
  //    public void validate() {
  //        super.validate();
  //        if (!world.isRemote && TechConfig.DEVICES.acceptIc2EU && !addedToIc2Network) {
  //            MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileLoadEvent(this));
  //            addedToIc2Network = true;
  //        }
  //    }

  @Override
  public void update() {
    if (world.isRemote) {
      IMachineSoundEffect.super.update();
      return;
    }
    IBlockState state = world.getBlockState(pos);
    int energyUsage = (int) (
      (float) TechConfig.DEVICES.electricForgeEnergyConsumption * targetTemperature / 100);
    if (energyUsage < 1) {
      energyUsage = 1;
    }
    for (int i = SLOT_INPUT_MIN; i <= SLOT_INPUT_MAX; i++) {
      ItemStack stack = inventory.getStackInSlot(i);
      var cap = CapabilityHeat.get(stack);
      float modifier =
        stack.getItem() instanceof ICapabilityMetal metal ? metal.getSmeltAmount(stack) / 100.0F
                                                          : 1.0F;
      if (cap != null) {
        // Update temperature of item
        float itemTemp = cap.getTemperature();
        int energy = (int) (energyUsage * modifier);
        if (targetTemperature > itemTemp && energyContainer.consumeEnergy(energy, false)) {
          float heatSpeed = (float) TechConfig.DEVICES.electricForgeSpeed * 15.0F;
          float temp = (float) (cap.getTemperature()
                                + heatSpeed * cap.getHeatCapacity() * ConfigCore.MISC.HEAT.globalModifier);
          cap.setTemperature(Math.min(temp, targetTemperature));
          litTime = 15;
        }
        handleInputMelting(stack, i);
      }
    }
    if (--litTime <= 0) {
      litTime = 0;
      state = state.withProperty(LIT, false);
      world.setBlockState(pos, state, 2);
    } else {
      state = state.withProperty(LIT, true);
      world.setBlockState(pos, state, 2);
    }
  }

  private void handleInputMelting(ItemStack stack, int index) {
    HeatRecipe recipe = cachedRecipes[index];
    var cap = CapabilityHeat.get(stack);

    if (recipe != null && cap != null && recipe.isValidTemperature(cap.getTemperature())) {
      // Handle possible metal output
      FluidStack fluidStack = recipe.getOutputFluid(stack);
      float itemTemperature = cap.getTemperature();
      if (fluidStack != null) {
        // Loop through all input slots
        for (int i = SLOT_EXTRA_MIN; i <= SLOT_EXTRA_MAX; i++) {
          // While the fluid is still waiting
          if (fluidStack.amount <= 0) {
            break;
          }
          // Try an output slot
          ItemStack output = inventory.getStackInSlot(i);
          // Fill the fluid
          IFluidHandler fluidHandler = output.getCapability(
            CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
          if (fluidHandler != null) {
            int amountFilled = fluidHandler.fill(fluidStack.copy(), true);
            if (amountFilled > 0) {
              fluidStack.amount -= amountFilled;

              // If the fluid was filled, make sure to make it the same temperature
              var heatHandler = CapabilityHeat.get(stack);
              if (heatHandler != null) {
                heatHandler.setTemperature(itemTemperature);
              }
            }
          }
        }
      }

      // Handle possible item output
      inventory.setStackInSlot(index, recipe.getOutputStack(stack));
    }
  }

  @Override
  public void setAndUpdateSlots(int slot) {
    this.markDirty();
    updateCachedRecipes();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    targetTemperature = nbt.getFloat("targetTemperature");
    energyContainer.deserializeNBT(nbt.getCompoundTag("energyContainer"));
    super.readFromNBT(nbt);

    updateCachedRecipes();
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setFloat("targetTemperature", targetTemperature);
    nbt.setTag("energyContainer", energyContainer.serializeNBT());
    return super.writeToNBT(nbt);
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    if (facing == null || facing == EnumFacing.UP || facing == EnumFacing.DOWN
        || facing == world.getBlockState(pos)
                          .getValue(HORIZONTAL)
                          .getOpposite()) {
      if (TechConfig.DEVICES.acceptFE && capability == CapabilityEnergy.ENERGY) {
        return true;
      } else if (TechConfig.DEVICES.acceptGTCEEU && GameUtils.isModLoaded("gregtech") &&
                 capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER) {
        return true;
      }
    }
    return super.hasCapability(capability, facing);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (facing == null || facing == EnumFacing.UP || facing == EnumFacing.DOWN
        || facing == world.getBlockState(pos)
                          .getValue(HORIZONTAL)
                          .getOpposite()) {
      if (TechConfig.DEVICES.acceptFE && capability == CapabilityEnergy.ENERGY) {
        return (T) this.energyContainer;
      } else if (TechConfig.DEVICES.acceptGTCEEU && GameUtils.isModLoaded("gregtech") &&
                 capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER) {
        return (T) this.energyContainer.getGTCEHandler();
      }
    }
    return super.getCapability(capability, facing);
  }

  private void updateCachedRecipes() {
    for (int i = SLOT_INPUT_MIN; i <= SLOT_INPUT_MAX; ++i) {
      this.cachedRecipes[i] = null;
      ItemStack inputStack = this.inventory.getStackInSlot(i);
      if (!inputStack.isEmpty()) {
        this.cachedRecipes[i] = HeatRecipe.get(inputStack);
      }
    }
  }

  @Override
  public int getSlotLimit(int slot) {
    return 1;
  }

  @Override
  public boolean isItemValid(int slot, @NotNull ItemStack stack) {
    if (slot <= SLOT_INPUT_MAX) {
      return CapabilityHeat.has(stack);
    } else {
      return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null) &&
             CapabilityHeat.has(stack);
    }
  }

  @Override
  public int getFieldCount() {
    return 2;
  }

  @Override
  public void setField(int index, int value) {
    if (index == 0) {
      this.targetTemperature = (float) value;
    } else if (index == 1) {
      this.energyContainer.setEnergy(value);
    } else {
      TFCTech.getLog().warn("Invalid field ID {} in TEElectricForge#setField", index);
    }
  }

  @Override
  public int getField(int index) {
    if (index == 0) {
      return (int) this.targetTemperature;
    } else if (index == 1) {
      return this.energyContainer.getEnergyStored();
    } else {
      TFCTech.getLog().warn("Invalid field ID {} in TEElectricForge#setField", index);
      return 0;
    }
  }

  public int getEnergyCapacity() {
    return energyContainer.getMaxEnergyStored();
  }

  @SideOnly(Side.CLIENT)
  @Override
  public SoundEvent getSoundEvent() {
    return SoundsDevice.INDUCTION_WORK;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public BlockPos getSoundPos() {
    return this.getPos();
  }

  @Override
  public boolean shouldPlay() {
    if (!this.isInvalid()) {
      return false;
    }
    IBlockState state = world.getBlockState(pos);
    return state.getBlock() instanceof BlockElectricForge && world.getBlockState(pos).getValue(LIT);
  }

  @Override
  public boolean isPlaying() {
    return soundPlay;
  }

  //    @Override
  //    public double getDemandedEnergy() {
  //        return Math.ceil(energyContainer.receiveEnergy(Integer.MAX_VALUE, true) / (double) TechConfig.DEVICES.ratioIc2);
  //    }
  //
  //    @Override
  //    public int getSinkTier() {
  //        return TechConfig.DEVICES.ic2Voltage;
  //    }
  //
  //    @Override
  //    public double injectEnergy(EnumFacing facing, double amount, double voltage) {
  //        energyContainer.receiveEnergy((int) Math.ceil(amount) * TechConfig.DEVICES.ratioIc2, false);
  //        return 0;
  //    }

  //    @Optional.Method(modid = "ic2")
  //    @Override
  //    public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, EnumFacing facing) {
  //        return TechConfig.DEVICES.acceptIc2EU && (facing == EnumFacing.UP || facing == EnumFacing.DOWN || facing == world.getBlockState(pos)
  //                .getValue(BlockElectricForge.FACING)
  //                .getOpposite());
  //    }

  @Override
  public void setPlaying(boolean value) {
    soundPlay = value;
  }

  public int getEnergyStored() {
    return energyContainer.getEnergyStored();
  }

  @Override
  public Optional<ModifierBase> getModifier(EntityPlayer player, TileEntity tile) {
    float temp = TileCrucible.FIELD_TEMPERATURE;
    float change = temp / 100f;
    float potency = temp / 350f;
    if (ModifierTile.hasProtection(player)) {
      change = change * 0.3F;
    }
    return ModifierBase.defined(this.getBlockType().getRegistryName().getPath(), change, potency);
  }

  @Override
  public ContainerElectricForge getContainer(InventoryPlayer inventoryPlayer, World world,
                                             IBlockState state, BlockPos pos) {
    return new ContainerElectricForge(inventoryPlayer, this);
  }

  @Override
  public GuiElectricForge getGuiContainer(InventoryPlayer inventoryPlayer, World world,
                                          IBlockState state, BlockPos pos) {
    return new GuiElectricForge(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
  }


}
