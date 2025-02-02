package su.terrafirmagreg.api.base.object.tile.spi;

import su.terrafirmagreg.api.base.object.inventory.api.slot.ISlotCallback;
import su.terrafirmagreg.api.base.object.inventory.spi.ItemStackHandlerCallback;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.function.BiFunction;

/**
 * This is a helper class for Tile's with a simple inventory that will respect automation To provide side based automation, you must expose a IItemHandler
 * wrapper based on the input side Without overriding the getCapability methods, this will not accept items from external automation
 */
@Getter
public abstract class BaseTileInventory extends BaseTile implements ISlotCallback {

  protected static final String TAG_INVENTORY = "Inventory";

  protected final ItemStackHandler inventory;

  protected BaseTileInventory(int inventorySize) {
    this.inventory = new ItemStackHandlerCallback(this, inventorySize);

  }

  protected BaseTileInventory(ItemStackHandler inventory) {
    this.inventory = inventory;

  }

  protected BaseTileInventory(BiFunction<ISlotCallback, Integer, ItemStackHandler> builder, int inventorySize) {
    this.inventory = builder.apply(this, inventorySize);

  }

  @Override
  public void setAndUpdateSlots(int slot) {
    this.markDirty();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    inventory.deserializeNBT(nbt.getCompoundTag(TAG_INVENTORY));
    super.readFromNBT(nbt);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    NBTUtils.setGenericNBTValue(nbt, TAG_INVENTORY, this.inventory.serializeNBT());
    return super.writeToNBT(nbt);
  }

  @Override
  public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
    return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == null) || super.hasCapability(capability, facing);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
    return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == null)
           ? (T) inventory
           : super.getCapability(capability, facing);
  }

  public void onBreakBlock(World world, BlockPos pos, IBlockState state) {
    for (int i = 0; i < inventory.getSlots(); i++) {
      StackUtils.spawnItemStack(world, pos, inventory.getStackInSlot(i));
    }
  }

  /**
   * Delegated from {@link Container#canInteractWith(EntityPlayer)}
   */
  public boolean canInteractWith(EntityPlayer player) {
    return TileUtils.getTile(world, pos).filter(tile -> tile == this).isPresent()
           && player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
  }
}
