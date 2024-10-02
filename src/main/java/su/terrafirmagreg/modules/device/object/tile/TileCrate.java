package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.tile.BaseTileInventory;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.device.client.gui.GuiCrate;
import su.terrafirmagreg.modules.device.object.container.ContainerCrate;
import su.terrafirmagreg.modules.device.object.inventory.InventoryLargeVessel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.inventory.IItemHandlerSidedCallback;
import net.dries007.tfc.api.capability.inventory.ItemHandlerSidedWrapper;
import net.dries007.tfc.objects.blocks.BlockLargeVessel;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendarFormatted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static su.terrafirmagreg.data.Properties.BoolProp.SEALED;

/**
 * @see BlockLargeVessel
 */

public class TileCrate extends BaseTileInventory implements IItemHandlerSidedCallback, IProviderContainer<ContainerCrate, GuiCrate> {

  @Getter
  private boolean sealed;
  private long sealedTick, sealedCalendarTick;

  public TileCrate() {
    super(new InventoryLargeVessel(15));
  }

  /**
   * Called when this TileEntity was created by placing a sealed Barrel Item. Loads its data from the Item's NBTTagCompound without loading xyz coordinates.
   *
   * @param nbt The NBTTagCompound to load from.
   */
  public void readFromItemTag(NBTTagCompound nbt) {
    inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
    sealedTick = nbt.getLong("sealedTick");
    sealedCalendarTick = nbt.getLong("sealedCalendarTick");
    sealed = true;
    markForSync();
  }

  /**
   * Called once per side when the TileEntity has finished loading. On servers, this is the earliest point in time to safely access the TE's World object.
   */
  @Override
  public void onLoad() {
    if (!world.isRemote) {
      sealed = world.getBlockState(pos).getValue(SEALED);
    }
  }

  @NotNull
  public String getSealedDate() {
    return ICalendarFormatted.getTimeAndDate(sealedCalendarTick, Calendar.CALENDAR_TIME.getDaysInMonth());
  }

  @Override
  public boolean canInsert(int slot, ItemStack stack, EnumFacing side) {
    return !world.getBlockState(pos).getValue(SEALED) && isItemValid(slot, stack);
  }

  @Override
  public boolean canExtract(int slot, EnumFacing side) {
    return !sealed;
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    ICapabilitySize sizeCap = CapabilitySize.getIItemSize(stack);
    if (sizeCap != null) {
      return sizeCap.getSize(stack).isSmallerThan(Size.LARGE);
    }
    return true;
  }

  public void onSealed() {
    for (int i = 0; i < inventory.getSlots(); i++) {
      CapabilityFood.applyTrait(inventory.getStackInSlot(i), FoodTrait.PRESERVED);
    }

    // Update sealed tick info and sync to client
    sealedTick = Calendar.PLAYER_TIME.getTicks();
    sealedCalendarTick = Calendar.CALENDAR_TIME.getTicks();
    sealed = true;
    markForSync();
  }

  public void onUnseal() {
    // Update preservation trait on contents
    for (int i = 0; i < inventory.getSlots(); i++) {
      CapabilityFood.removeTrait(inventory.getStackInSlot(i), FoodTrait.PRESERVED);
    }

    // Update sealed tick info and sync to client
    sealedTick = sealedCalendarTick = 0;
    sealed = false;
    markForSync();
  }

  @Override
  public void readFromNBT(@NotNull NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    sealedTick = nbt.getLong("sealedTick");
    sealedCalendarTick = nbt.getLong("sealedCalendarTick");
    sealed = sealedTick > 0;
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
    NBTUtils.setGenericNBTValue(nbt, "sealedTick", sealedTick);
    NBTUtils.setGenericNBTValue(nbt, "sealedCalendarTick", sealedCalendarTick);
    return super.writeToNBT(nbt);
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return (T) new ItemHandlerSidedWrapper(this, inventory, facing);
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public void onBreakBlock(World world, BlockPos pos, IBlockState state) {
    if (!state.getValue(SEALED)) {
      // Not sealed, so empty contents normally
      super.onBreakBlock(world, pos, state);
    } else {
      // Need to create the full barrel and drop it now
      ItemStack stack = new ItemStack(state.getBlock());
      stack.setTagCompound(getItemTag());
      StackUtils.spawnItemStack(world, pos, stack);
    }
  }

  /**
   * Called to get the NBTTagCompound that is put on Barrel Items. This happens when a sealed Barrel was broken.
   *
   * @return An NBTTagCompound containing inventory and tank data.
   */
  private NBTTagCompound getItemTag() {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setTag("inventory", inventory.serializeNBT());
    nbt.setLong("sealedTick", sealedTick);
    nbt.setLong("sealedCalendarTick", sealedCalendarTick);
    return nbt;
  }

  @Override
  public ContainerCrate getContainer(InventoryPlayer inventoryPlayer, World world,
                                     IBlockState state, BlockPos pos) {
    return new ContainerCrate(inventoryPlayer, this);
  }

  @Override
  public GuiCrate getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new GuiCrate(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this, state);
  }


}
