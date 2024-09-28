package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.tile.BaseTileTickableInventory;
import su.terrafirmagreg.api.base.tile.spi.ITileFields;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.device.client.gui.GuiCrucible;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.object.container.ContainerCrucible;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.ISmallVesselHandler;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.inventory.IItemHandlerSidedCallback;
import net.dries007.tfc.api.capability.inventory.ItemHandlerSidedWrapper;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.util.Alloy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class TileCrucible extends BaseTileTickableInventory
  implements ITileFields, IItemHandlerSidedCallback,
             IProviderContainer<ContainerCrucible, GuiCrucible> {

  public static final int SLOT_INPUT_START = 0;
  public static final int SLOT_INPUT_END = 8;
  public static final int SLOT_OUTPUT = 9;

  public static final int FIELD_TEMPERATURE = 0;

  private final Alloy alloy;
  private final IItemHandler inventoryWrapperExtract;
  private final IItemHandler inventoryWrapperInsert;

  private final HeatRecipe[] cachedRecipes;
  private Metal alloyResult;
  private float temperature;
  private float targetTemperature;
  private int lastFillTimer;

  public TileCrucible() {
    super(10);

    this.alloy = new Alloy(ConfigDevice.BLOCK.CRUCIBLE.tank); // Side effect: Maximum amount only matches config if not loading from disk
    this.inventoryWrapperExtract = new ItemHandlerSidedWrapper(this, inventory, EnumFacing.DOWN);
    this.inventoryWrapperInsert = new ItemHandlerSidedWrapper(this, inventory, EnumFacing.UP);

    this.temperature = 0;
    this.lastFillTimer = 0;
    this.cachedRecipes = new HeatRecipe[9];
    Arrays.fill(this.cachedRecipes, null);
  }

  public void acceptHeat(float temperature) {
    if (temperature > targetTemperature) {
      targetTemperature = temperature;
    }
  }

  public int addMetal(Metal metal, int amount) {
    int overflow = Math.max(0,
                            alloy.getAmount() + amount - alloy.getMaxAmount()); // Amount which cannot be inserted
    alloy.add(metal, amount);

    // Update crucible temperature to match
    temperature = metal.getMeltTemp();
    targetTemperature = metal.getMeltTemp();

    // Alloy changed, so sync to client
    markForSync();
    return overflow;
  }

  @Override
  public void update() {
    super.update();
    if (!world.isRemote) {
      temperature = CapabilityHeat.adjustTempTowards(temperature, targetTemperature,
                                                     (float) ConfigCore.MISC.HEAT.heatingModifier);
      if (targetTemperature > 0) {
        // Crucible target temperature decays constantly, since it is set by outside providers
        targetTemperature -= (float) ConfigCore.MISC.HEAT.heatingModifier;
      }

      // Input draining
      boolean canFill = lastFillTimer <= 0;
      for (int i = SLOT_INPUT_START; i <= SLOT_INPUT_END; i++) {
        ItemStack inputStack = inventory.getStackInSlot(i);
        var cap = CapabilityHeat.get(inputStack);
        if (cap != null) {
          // Always heat up the item regardless if it is melting or not
          if (cap.getTemperature() < temperature) {
            CapabilityHeat.addTemp(cap, temperature / 400 + 2);
          }
          if (cachedRecipes[i] != null) {
            if (cachedRecipes[i].isValidTemperature(cap.getTemperature())) {
              alloy.add(inputStack, cachedRecipes[i]);

              ItemStack outputStack = cachedRecipes[i].getOutputStack(inputStack);
              CapabilityFood.applyTrait(outputStack, FoodTrait.BURNT_TO_A_CRISP);
              inventory.setStackInSlot(i, outputStack);
              // Update reference since it may have changed from recipe output
              inputStack = inventory.getStackInSlot(i);
              cap = CapabilityHeat.get(inputStack);
              markForSync();
            }
          }
        }
        // Try and drain fluid
        if (cap instanceof IMoldHandler mold) {
          if (canFill) {
            if (mold.isMolten()) {
              // Use mold.getMetal() to avoid off by one errors during draining
              Metal metal = mold.getMetal();
              FluidStack fluidStack = mold.drain(ConfigDevice.BLOCK.CRUCIBLE.pouringSpeed, true);
              if (fluidStack != null && fluidStack.amount > 0) {
                lastFillTimer = 5;
                if (!ConfigDevice.BLOCK.CRUCIBLE.enableAllSlots) {
                  canFill = false;
                }
                alloy.add(metal, fluidStack.amount);
                markForSync();
              }
            }
          }
        }
      }
      if (lastFillTimer > 0) {
        lastFillTimer--;
      }

      // Output filling
      ItemStack outputStack = inventory.getStackInSlot(SLOT_OUTPUT);
      var capOut = CapabilityHeat.get(outputStack);
      if (capOut instanceof IMoldHandler mold) {

        // Check that the crucible metal is molten
        Metal alloyMetal = alloy.getResult();
        if (temperature > alloyMetal.getMeltTemp()) {
          // Fill from the current alloy
          int amountToFill = alloy.removeAlloy(1, true);
          if (amountToFill > 0) {
            // Do fill of the mold
            Fluid metalFluid = FluidsTFC.getFluidFromMetal(alloyMetal);
            FluidStack fluidStack = new FluidStack(metalFluid, amountToFill);
            int amountFilled = mold.fill(fluidStack, true);

            if (amountFilled > 0) {
              // Actually remove fluid from the alloy
              alloy.removeAlloy(amountFilled, false);

              // Set the output item to high temperature
              capOut.setTemperature(temperature);
              markForSync();
            }
          }
        }
      }
      if (needsClientUpdate) {
        // Update cached alloy result, since TOP is executed server side.
        alloyResult = alloy.getResult();
      }
    }
  }

  @Override
  public int getSlotLimit(int slot) {
    return 1;
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    if (!CapabilityHeat.has(stack)) {
      return false;
    }
    if (slot != SLOT_OUTPUT) {
      IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
                                              null);
      if (cap instanceof IMoldHandler) {
        if (cap instanceof ISmallVesselHandler) {
          if (((ISmallVesselHandler) cap).getMetal() != null) {
            return true;
          } else {
            for (int i = 0; i < ((ISmallVesselHandler) cap).getSlots(); i++) {
              if (!((ISmallVesselHandler) cap).getStackInSlot(i).isEmpty()) {
                return true;
              }
            }
            return false; // This will make empty small vessels go to the output slot (same as below)
          }
        } else {
          return ((IMoldHandler) cap).getAmount() >
                 0; // This will make empty molds go to the output slot / prevent empty molds go to the input (no sense in heating them here anyway)
        }
      }
    }
    return slot != SLOT_OUTPUT || stack.hasCapability(
      CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
  }

  @Override
  public void setAndUpdateSlots(int slot) {
    super.setAndUpdateSlots(slot);
    if (slot != SLOT_OUTPUT) {
      cachedRecipes[slot] = HeatRecipe.get(inventory.getStackInSlot(slot));
    }
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    alloy.deserializeNBT(nbt.getCompoundTag("alloy"));
    temperature = nbt.getFloat("temp");

    // Voids surplus and set the maximum amount if config was changed
    alloy.setMaxAmount(ConfigDevice.BLOCK.CRUCIBLE.tank);

    // Also set the cached alloyResult:
    alloyResult = alloy.getResult();

    super.readFromNBT(nbt);

    // Update the recipe cache
    for (int i = SLOT_INPUT_START; i <= SLOT_INPUT_END; i++) {
      cachedRecipes[i] = HeatRecipe.get(inventory.getStackInSlot(i));
    }
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    NBTUtils.setGenericNBTValue(nbt, "alloy", alloy.serializeNBT());
    NBTUtils.setGenericNBTValue(nbt, "temp", temperature);
    return super.writeToNBT(nbt);
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != null)
           || super.hasCapability(capability, facing);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != null) {
      if (facing == EnumFacing.DOWN) {
        return (T) inventoryWrapperExtract;
      } else {
        return (T) inventoryWrapperInsert;
      }
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public void onBreakBlock(World world, BlockPos pos, IBlockState state) {
    // Only carry to itemstack the alloy fluid
    super.onBreakBlock(world, pos, state);
    ItemStack stack = new ItemStack(BlocksDevice.CRUCIBLE);
    if (alloy.getAmount() > 0) {
      stack.setTagCompound(this.writeToItemTag());
    }
    StackUtils.spawnItemStack(world, pos, stack);
  }

  public NBTTagCompound writeToItemTag() {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setTag("alloy", alloy.serializeNBT());
    return nbt;
  }

  @Override
  public int getFieldCount() {
    return 1;
  }

  @Override
  public void setField(int index, int value) {
    if (index == FIELD_TEMPERATURE) {
      this.temperature = value;
      return;
    }
    ModuleDevice.LOGGER.warn("Illegal field id {} in TECrucible#setField", index);
  }

  @Override
  public int getField(int index) {
    if (index == FIELD_TEMPERATURE) {
      return (int) temperature;
    }
    ModuleDevice.LOGGER.warn("Illegal field id {} in TECrucible#getField", index);
    return 0;
  }

  public void readFromItemTag(NBTTagCompound nbt) {
    alloy.deserializeNBT(nbt.getCompoundTag("alloy"));

    // Voids surplus and set the maximum amount if config was changed
    alloy.setMaxAmount(ConfigDevice.BLOCK.CRUCIBLE.tank);

    // Also set the cached alloyResult:
    alloyResult = alloy.getResult();
  }

  /**
   * Used on SERVER to get the alloy contents
   *
   * @return the alloyForgeableHeatableHandler
   */
  @NotNull
  public Alloy getAlloy() {
    return alloy;
  }

  /**
   * Used on CLIENT for quicker rendering - doesn't have to calculate the alloy every render tick
   *
   * @return the current result of getAlloy().getResult()
   */
  @NotNull
  public Metal getAlloyResult() {
    return alloyResult;
  }

  @Override
  public boolean canInsert(int slot, ItemStack stack, EnumFacing side) {
    IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    if (cap instanceof IMoldHandler) {
      // Molds will go into the output slot (automating filling molds should be possible)
      return side != EnumFacing.DOWN && slot == SLOT_OUTPUT;
    }
    return side != EnumFacing.DOWN && slot != SLOT_OUTPUT;
  }

  @Override
  public boolean canExtract(int slot, EnumFacing side) {
    if (side == EnumFacing.DOWN && slot == SLOT_OUTPUT) {
      ItemStack stack = inventory.getStackInSlot(SLOT_OUTPUT);
      IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
                                              null);
      if (cap != null) {
        // Only output if cap is full (so, only full molds will output from slot)
        return cap.drain(1, false) != null && cap.fill(cap.drain(1, false), false) <= 0;
      }
      return true;
    }
    return false;
  }

  @Override
  public ContainerCrucible getContainer(InventoryPlayer inventoryPlayer, World world,
                                        IBlockState state, BlockPos pos) {
    return new ContainerCrucible(inventoryPlayer, this);
  }

  @Override
  public GuiCrucible getGuiContainer(InventoryPlayer inventoryPlayer, World world,
                                     IBlockState state, BlockPos pos) {
    return new GuiCrucible(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
  }
}
