package net.dries007.tfc.objects.te;

import su.terrafirmagreg.api.base.tile.BaseTileInventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendarFormatted;
import pieman.caffeineaddon.recipes.DryingMatRecipe;

import org.jetbrains.annotations.NotNull;

public class TEDryingMat extends BaseTileInventory implements ITickable {

  public static final int SLOT = 0;
  //tickcounter
  private long lastUpdateTick;
  private long sealedCalendarTick;

  public TEDryingMat() {
    super(1);
  }

  public ItemStack insertOrSwapItem(int slot, ItemStack playerStack) {
    ItemStack quernStack = inventory.getStackInSlot(slot);

    if (quernStack.isEmpty() || (playerStack.isStackable() && quernStack.isStackable()
            && quernStack.getItem() == playerStack.getItem()
            && (!playerStack.getHasSubtypes() || playerStack.getMetadata() == quernStack.getMetadata())
            && ItemStack.areItemStackTagsEqual(playerStack, quernStack))) {
      return inventory.insertItem(slot, playerStack, false);
    }
    inventory.setStackInSlot(slot, playerStack);
    return quernStack;
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    if (slot == SLOT) {
      return DryingMatRecipe.get(stack) != null;
    }
    return false;
  }

  @Override
  public void setAndUpdateSlots(int slot) {
    this.resetCounter();
    super.setAndUpdateSlots(slot);
  }

  public void resetCounter() {
    lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
    sealedCalendarTick = Calendar.CALENDAR_TIME.getTicks();
    markDirty();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    lastUpdateTick = nbt.getLong("tick");
    sealedCalendarTick = nbt.getLong("date");
    super.readFromNBT(nbt);
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setLong("tick", lastUpdateTick);
    nbt.setLong("date", sealedCalendarTick);
    return super.writeToNBT(nbt);
  }
  //-----

  public void reduceCounter(long amount) {
    lastUpdateTick += amount;
    markDirty();
  }

  @NotNull
  public String getSealedDate() {
    return getStack().isEmpty() ? "" : ICalendarFormatted.getTimeAndDate(sealedCalendarTick, Calendar.CALENDAR_TIME.getDaysInMonth());
  }

  public ItemStack getStack() {
    return inventory.getStackInSlot(SLOT);
  }

  public void setStack(ItemStack stack) {
    inventory.setStackInSlot(SLOT, stack);
    markDirty();
  }

  @NotNull
  public String getOutputName() {
    return DryingMatRecipe.get(getStack()) == null ? "" : DryingMatRecipe.get(getStack())
            .getOutputStack()
            .getDisplayName();
  }

  @NotNull
  public float getProgress() {
    return DryingMatRecipe.get(getStack()) == null ? 0 : ((float) getTicksSinceUpdate()) / ((float) DryingMatRecipe.get(getStack())
            .getDuration());
  }

  public long getTicksSinceUpdate() {
    return Calendar.PLAYER_TIME.getTicks() - lastUpdateTick;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public double getMaxRenderDistanceSquared() {
    return 1024.0D;
  }

  @Override
  @NotNull
  @SideOnly(Side.CLIENT)
  public AxisAlignedBB getRenderBoundingBox() {
    return new AxisAlignedBB(getPos(), getPos().add(1D, 1D, 1D));
  }

  @Override
  public void update() {
    checkDry();
  }

  public void checkDry() {
    if (!inventory.getStackInSlot(SLOT).isEmpty()) {
      ItemStack stack = inventory.getStackInSlot(SLOT);
      DryingMatRecipe recipe = DryingMatRecipe.get(stack);
      if (recipe != null && !world.isRemote && getTicksSinceUpdate() >= recipe.getDuration()) {
        setStack(new ItemStack(recipe.getOutputItem(stack).getItem(), stack.getCount()));
        resetCounter();
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
      }
    }
  }

}
