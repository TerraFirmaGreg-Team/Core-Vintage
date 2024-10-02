package su.terrafirmagreg.modules.wood.object.tile;

import su.terrafirmagreg.api.base.tile.BaseTileTickableInventory;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.wood.ConfigWood;
import su.terrafirmagreg.modules.wood.client.gui.GuiWoodBarrel;
import su.terrafirmagreg.modules.wood.object.container.ContainerWoodBarrel;
import su.terrafirmagreg.modules.wood.object.itemblock.ItemBlockWoodBarrel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import net.dries007.tfc.api.capability.fluid.FluidHandlerSided;
import net.dries007.tfc.api.capability.fluid.FluidTankCallback;
import net.dries007.tfc.api.capability.fluid.IFluidHandlerSidedCallback;
import net.dries007.tfc.api.capability.fluid.IFluidTankCallback;
import net.dries007.tfc.api.capability.inventory.IItemHandlerSidedCallback;
import net.dries007.tfc.api.capability.inventory.ItemHandlerSidedWrapper;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.util.FluidTransferHelper;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendarFormatted;
import net.dries007.tfc.util.calendar.ICalendarTickable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static su.terrafirmagreg.data.Properties.BoolProp.SEALED;

public class TileWoodBarrel extends BaseTileTickableInventory
  implements ICalendarTickable, IItemHandlerSidedCallback, IFluidHandlerSidedCallback,
             IFluidTankCallback, IProviderContainer<ContainerWoodBarrel, GuiWoodBarrel> {

  public static final int SLOT_FLUID_CONTAINER_IN = 0;
  public static final int SLOT_FLUID_CONTAINER_OUT = 1;
  public static final int SLOT_ITEM = 2;
  public static final int BARREL_MAX_FLUID_TEMPERATURE = 500;

  private final FluidTank tank = new BarrelFluidTank(this, 0);
  private final Queue<ItemStack> surplus = new LinkedList<>(); // Surplus items from a recipe with output > stackSize
  @Getter
  private boolean sealed;
  private long sealedTick;
  private long sealedCalendarTick;
  private long lastPlayerTick; // Last player tick this barrel was ticked (for purposes of catching up)
  private BarrelRecipe recipe;
  private int tickCounter;
  private boolean checkInstantRecipe = false;

  public TileWoodBarrel() {
    super(3);
  }

  /**
   * Load up item and fluid handler contents from a barrel's ItemStack
   *
   * @param stack the barrel's stack to load contents from
   */
  public void loadFromItemStack(ItemStack stack) {
    IFluidHandler barrelCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (barrelCap instanceof ItemBlockWoodBarrel.ItemBarrelFluidHandler itemBarrelFluidHandler) {
      NBTTagCompound contents = itemBarrelFluidHandler.getBarrelContents();
      if (contents != null) {
        inventory.deserializeNBT(contents.getCompoundTag("inventory"));
        surplus.clear();
        NBTTagList surplusItems = contents.getTagList("surplus", Constants.NBT.TAG_COMPOUND);
        if (!surplusItems.isEmpty()) {
          for (int i = 0; i < surplusItems.tagCount(); i++) {
            surplus.add(new ItemStack(surplusItems.getCompoundTagAt(i)));
          }
        }
        sealedTick = contents.getLong("sealedTick");
        sealedCalendarTick = contents.getLong("sealedCalendarTick");
        tank.fill(itemBarrelFluidHandler.getFluid(), true);
        sealed = true;
        recipe = BarrelRecipe.get(inventory.getStackInSlot(SLOT_ITEM), tank.getFluid());
        markForSync();
      }
    }
  }

  /**
   * Called once per side when the TileEntity has finished loading. On servers, this is the earliest point in time to safely access the TE's World object.
   */
  @Override
  public void onLoad() {
    if (!world.isRemote) {
      sealed = world.getBlockState(pos).getValue(SEALED);
      recipe = BarrelRecipe.get(inventory.getStackInSlot(SLOT_ITEM), tank.getFluid());
    }
  }

  @Nullable
  public BarrelRecipe getRecipe() {
    return recipe;
  }

  @NotNull
  public String getSealedDate() {
    return ICalendarFormatted.getTimeAndDate(sealedCalendarTick,
                                             Calendar.CALENDAR_TIME.getDaysInMonth());
  }

  @Override
  public void setAndUpdateFluidTank(int fluidTankID) {
    markForSync();
  }

  @Override
  public boolean canInsert(int slot, ItemStack stack, EnumFacing side) {
    return !sealed && (isItemValid(slot, stack)
                       || side == null && slot == SLOT_FLUID_CONTAINER_OUT);
  }

  @Override
  public boolean canExtract(int slot, EnumFacing side) {
    return !sealed && (side == null || slot != SLOT_FLUID_CONTAINER_IN);
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    switch (slot) {
      case SLOT_FLUID_CONTAINER_IN -> {
        return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
      }
      case SLOT_ITEM -> {
        ICapabilitySize size = CapabilitySize.getIItemSize(stack);
        if (size != null) {
          return size.getSize(stack).isSmallerThan(Size.HUGE);
        }
        return true;
      }
      default -> {
        return false;
      }
    }
  }

  @Override
  public boolean canFill(FluidStack resource, EnumFacing side) {
    return !sealed && (resource.getFluid() == null || resource.getFluid()
                                                              .getTemperature(resource) < BARREL_MAX_FLUID_TEMPERATURE);
  }

  @Override
  public boolean canDrain(EnumFacing side) {
    return !sealed;
  }

  public void onUnseal() {
    sealedTick = sealedCalendarTick = 0;
    if (recipe != null) {
      ItemStack inputStack = inventory.getStackInSlot(SLOT_ITEM);
      FluidStack inputFluid = tank.getFluid();
      if (recipe.isValidInput(inputFluid, inputStack)) {
        tank.setFluid(recipe.getOutputFluidOnUnseal(inputFluid, inputStack));
        List<ItemStack> output = recipe.getOutputItemOnUnseal(inputFluid, inputStack);
        ItemStack first = output.get(0);
        output.remove(0);
        inventory.setStackInSlot(SLOT_ITEM, first);
        surplus.addAll(output);
      }
    }
    recipe = null;
    sealed = false;
    markForSync();
  }

  @Override
  public void update() {
    super.update();
    checkForCalendarUpdate();
    if (!world.isRemote) {
      tickCounter++;
      if (tickCounter == 10) {
        tickCounter = 0;

        ItemStack fluidContainerIn = inventory.getStackInSlot(SLOT_FLUID_CONTAINER_IN);
        FluidActionResult result = FluidTransferHelper.emptyContainerIntoTank(fluidContainerIn,
                                                                              tank, inventory, SLOT_FLUID_CONTAINER_OUT,
                                                                              ConfigWood.BLOCK.BARREL.tank, world, pos);

        if (!result.isSuccess()) {
          result = FluidTransferHelper.fillContainerFromTank(fluidContainerIn, tank, inventory,
                                                             SLOT_FLUID_CONTAINER_OUT,
                                                             ConfigWood.BLOCK.BARREL.tank, world, pos);
        }

        if (result.isSuccess()) {
          inventory.setStackInSlot(SLOT_FLUID_CONTAINER_IN, result.getResult());
        }

        Fluid freshWater = FluidRegistry.getFluid("fresh_water");

        if (!sealed && world.isRainingAt(pos.up()) && (tank.getFluid() == null || tank.getFluid()
                                                                                      .getFluid() == freshWater)) {
          tank.fill(new FluidStack(freshWater, 10), true);
        }

        if (inventory.getStackInSlot(SLOT_ITEM) == ItemStack.EMPTY && !surplus.isEmpty()) {
          inventory.setStackInSlot(SLOT_ITEM, surplus.poll());
        }
      }

      // Check if recipe is complete (sealed recipes only)
      if (recipe != null && sealed) {
        int durationSealed = (int) (Calendar.PLAYER_TIME.getTicks() - sealedTick);
        if (recipe.getDuration() > 0 && durationSealed > recipe.getDuration()) {
          ItemStack inputStack = inventory.getStackInSlot(SLOT_ITEM);
          FluidStack inputFluid = tank.getFluid();
          if (recipe.isValidInput(inputFluid, inputStack)) {
            tank.setFluid(recipe.getOutputFluid(inputFluid, inputStack));
            List<ItemStack> output = recipe.getOutputItem(inputFluid, inputStack);
            ItemStack first = output.get(0);
            output.remove(0);
            inventory.setStackInSlot(SLOT_ITEM, first);
            surplus.addAll(output);
            markForSync();
            onSealed(); //run the sealed check again in case we have a new valid recipe.
          } else {
            recipe = null;
          }
        }
      }

      if (checkInstantRecipe) {
        ItemStack inputStack = inventory.getStackInSlot(SLOT_ITEM);
        FluidStack inputFluid = tank.getFluid();
        BarrelRecipe instantRecipe = BarrelRecipe.getInstant(inputStack, inputFluid);
        if (instantRecipe != null && inputFluid != null && instantRecipe.isValidInputInstant(
          inputStack, inputFluid)) {
          tank.setFluid(instantRecipe.getOutputFluid(inputFluid, inputStack));
          List<ItemStack> output = instantRecipe.getOutputItem(inputFluid, inputStack);
          ItemStack first = output.get(0);
          output.remove(0);
          inventory.setStackInSlot(SLOT_ITEM, first);
          surplus.addAll(output);
          instantRecipe.onRecipeComplete(world, pos);
          markForSync();
        } else {
          checkInstantRecipe = false;
        }
      }
    }
  }

  public void onSealed() {
    if (!world.isRemote) {
      for (int slot : new int[]{SLOT_FLUID_CONTAINER_IN, SLOT_FLUID_CONTAINER_OUT}) {
        StackUtils.spawnItemStack(world, pos, inventory.getStackInSlot(slot));
        inventory.setStackInSlot(slot, ItemStack.EMPTY);
      }
    }

    sealedTick = Calendar.PLAYER_TIME.getTicks();
    sealedCalendarTick = Calendar.CALENDAR_TIME.getTicks();
    recipe = BarrelRecipe.get(inventory.getStackInSlot(SLOT_ITEM), tank.getFluid());
    if (recipe != null) {
      recipe.onBarrelSealed(tank.getFluid(), inventory.getStackInSlot(SLOT_ITEM));
    }
    sealed = true;
    markForSync();
  }

  @Override
  public void setAndUpdateSlots(int slot) {
    checkInstantRecipe = true;
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
    sealedTick = nbt.getLong("sealedTick");
    sealedCalendarTick = nbt.getLong("sealedCalendarTick");
    sealed = nbt.getBoolean("sealed");
    lastPlayerTick = nbt.getLong("lastPlayerTick");

    surplus.clear();
    if (nbt.hasKey("surplus")) {
      NBTTagList surplusItems = nbt.getTagList("surplus", Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < surplusItems.tagCount(); i++) {
        surplus.add(new ItemStack(surplusItems.getCompoundTagAt(i)));
      }
    }

    recipe = BarrelRecipe.get(inventory.getStackInSlot(SLOT_ITEM), tank.getFluid());
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    NBTUtils.setGenericNBTValue(nbt, "tank", tank.writeToNBT(new NBTTagCompound()));
    NBTUtils.setGenericNBTValue(nbt, "sealedTick", sealedTick);
    NBTUtils.setGenericNBTValue(nbt, "sealedCalendarTick", sealedCalendarTick);
    NBTUtils.setGenericNBTValue(nbt, "sealed", sealed);
    NBTUtils.setGenericNBTValue(nbt, "lastPlayerTick", lastPlayerTick);

    if (!surplus.isEmpty()) {
      NBTTagList surplusList = new NBTTagList();
      for (ItemStack stack : surplus) {
        surplusList.appendTag(stack.serializeNBT());
      }
      NBTUtils.setGenericNBTValue(nbt, "surplus", surplusList);
    }

    return super.writeToNBT(nbt);
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
           || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return (T) new ItemHandlerSidedWrapper(this, inventory, facing);
    }

    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
      return (T) new FluidHandlerSided(this, tank, facing);
    }

    return super.getCapability(capability, facing);
  }

  @Override
  public void onBreakBlock(World world, BlockPos pos, IBlockState state) {
    ItemStack barrelStack = new ItemStack(state.getBlock());

    if (state.getValue(SEALED)) {
      saveToItemStack(barrelStack);
      StackUtils.spawnItemStack(world, pos, barrelStack);
    } else {
      for (int i = 0; i < inventory.getSlots(); i++) {
        StackUtils.spawnItemStack(world, pos, inventory.getStackInSlot(i));
        inventory.setStackInSlot(i, new ItemStack(Items.AIR, 0));
      }
      if (!surplus.isEmpty()) {
        for (ItemStack surplusToDrop : surplus) {
          StackUtils.spawnItemStack(world, pos, surplusToDrop);
        }
        surplus.clear();
      }
      StackUtils.spawnItemStack(world, pos, barrelStack);
    }
  }

  /**
   * Save up item and fluid handler contents to a barrel's ItemStack
   *
   * @param stack the barrel's stack to save contents to
   */
  public void saveToItemStack(ItemStack stack) {
    IFluidHandler barrelCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (barrelCap instanceof ItemBlockWoodBarrel.ItemBarrelFluidHandler itemBarrelFluidHandler) {
      NBTTagCompound inventoryTag = null;
      // Check if inventory has contents
      for (int i = 0; i < inventory.getSlots(); i++) {
        if (!inventory.getStackInSlot(i).isEmpty()) {
          inventoryTag = inventory.serializeNBT();
          break;
        }
      }
      NBTTagList surplusTag = null;
      // Check if there's remaining surplus from recipe
      if (!surplus.isEmpty()) {
        surplusTag = new NBTTagList();
        for (ItemStack surplusStack : surplus) {
          surplusTag.appendTag(surplusStack.serializeNBT());
        }
      }
      FluidStack storing = tank.getFluid();
      if (storing != null || inventoryTag != null || surplusTag != null) {
        itemBarrelFluidHandler.setBarrelContents(storing, inventoryTag, surplusTag, sealedTick,
                                                 sealedCalendarTick);
      }
    }
  }

  @Override
  public long getLastUpdateTick() {
    return lastPlayerTick;
  }

  @Override
  public void setLastUpdateTick(long tick) {
    this.lastPlayerTick = tick;
  }

  @Override
  public void onCalendarUpdate(long deltaPlayerTicks) {
    while (deltaPlayerTicks > 0) {
      deltaPlayerTicks = 0;
      if (recipe != null && sealed && recipe.getDuration() > 0) {
        long tickFinish = sealedTick + recipe.getDuration();
        if (tickFinish <= Calendar.PLAYER_TIME.getTicks()) {
          // Mark to run this transaction again in case this recipe produces valid output for another which could potentially finish in this time period.
          deltaPlayerTicks = 1;
          long offset = tickFinish - Calendar.PLAYER_TIME.getTicks();

          Calendar.runTransaction(offset, offset, () -> {
            ItemStack inputStack = inventory.getStackInSlot(SLOT_ITEM);
            FluidStack inputFluid = tank.getFluid();
            if (recipe.isValidInput(inputFluid, inputStack)) {
              tank.setFluid(recipe.getOutputFluid(inputFluid, inputStack));
              List<ItemStack> output = recipe.getOutputItem(inputFluid, inputStack);
              ItemStack first = output.get(0);
              output.remove(0);
              inventory.setStackInSlot(SLOT_ITEM, first);
              surplus.addAll(output);
              markForSync();
              onSealed(); //run the sealed check again in case we have a new valid recipe.
            } else {
              recipe = null;
            }
          });
        }
      }
    }
  }

  @Override
  public ContainerWoodBarrel getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new ContainerWoodBarrel(inventoryPlayer, this);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public GuiWoodBarrel getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new GuiWoodBarrel(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this, state);
  }

  protected static class BarrelFluidTank extends FluidTankCallback {

    private final Set<Fluid> whitelist;

    public BarrelFluidTank(IFluidTankCallback callback, int fluidTankID) {
      super(callback, fluidTankID, ConfigWood.BLOCK.BARREL.tank);
      whitelist = Arrays.stream(ConfigWood.BLOCK.BARREL.fluidWhitelist)
                        .map(FluidRegistry::getFluid)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
      return fluid != null && (whitelist.contains(fluid.getFluid()) || BarrelRecipe.isBarrelFluid(fluid));
    }
  }


}
