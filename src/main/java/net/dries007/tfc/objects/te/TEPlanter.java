package net.dries007.tfc.objects.te;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendarTickable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.firmalife.init.StatePropertiesFL;
import net.dries007.firmalife.util.GreenhouseHelpers;
import net.dries007.firmalife.util.IWaterable;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.objects.recipes.PlanterRecipe;

import javax.annotation.Nonnull;

/**
 * Evil combination of TEInventory and TECropBase because I can't code
 */
public class TEPlanter extends TEInventory implements ITickable, ICalendarTickable, IWaterable, GreenhouseHelpers.IGreenhouseReceiver {

  public boolean isClimateValid;
  protected int[] stages;
  protected long lastUpdateTick;
  protected long lastTickCalChecked;
  protected int waterUses;
  protected int tier;

  public TEPlanter() {
    super(4);
    stages = new int[]{0, 0, 0, 0};
    lastUpdateTick = 0;
    lastTickCalChecked = Calendar.PLAYER_TIME.getTicks();
    waterUses = 0;
    tier = 0;
    isClimateValid = false;
  }

  @Override
  public void update() {
    ICalendarTickable.super.checkForCalendarUpdate();
    if (waterUses < 0) {
      waterUses = 0;
      world.setBlockState(pos, world.getBlockState(pos).withProperty(StatePropertiesFL.WET, false));
    }
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    stages = nbt.getIntArray("stages");
    lastUpdateTick = nbt.getLong("tick");
    lastTickCalChecked = nbt.getLong("lastTickCalChecked");
    waterUses = nbt.getInteger("waterUses");
    isClimateValid = nbt.getBoolean("isClimateValid");
    tier = nbt.getInteger("tier");
    super.readFromNBT(nbt);
  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setIntArray("stages", stages);
    nbt.setLong("tick", lastUpdateTick);
    nbt.setLong("lastTickCalChecked", lastTickCalChecked);
    nbt.setInteger("waterUses", waterUses);
    nbt.setBoolean("isClimateValid", isClimateValid);
    nbt.setInteger("tier", tier);
    return super.writeToNBT(nbt);
  }

  @Override
  public int getSlotLimit(int slot) {
    return 1;
  }

  public void onInsert(int slot) {
    stages[slot] = 0;
    markForSync();
    markForBlockUpdate();
  }

  public void grow(int slot) {
    PlanterRecipe recipe = getRecipe(slot);
    if (recipe != null && getStage(slot) < PlanterRecipe.getMaxStage(recipe)) {
      stages[slot] = Math.min(PlanterRecipe.getMaxStage(recipe), getStage(slot) + 1);
      waterUses--;
    }
    markForSync();
  }

  public long getTicksSinceUpdate() {
    return Calendar.PLAYER_TIME.getTicks() - lastUpdateTick;
  }

  public void resetCounter() {
    lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
    markForSync();
  }

  public void reduceCounter(long amount) {
    lastUpdateTick += amount;
    markForSync();
  }

  @Override
  public void onCalendarUpdate(long l) {
    double tierModifier = tier >= 2 ? 0.95D : 1.05D;
    long growthTicks = (long) (ICalendar.TICKS_IN_DAY * tierModifier * ConfigTFC.General.FOOD.cropGrowthTimeModifier);
    while (getTicksSinceUpdate() > growthTicks) {
      reduceCounter(growthTicks);
      int slot = Constants.RNG.nextInt(4);
      if (waterUses < 0) {
        resetCounter();
        return;
      }
      if (canGrow(slot)) {
        grow(slot);
      }
    }
  }

  @Override
  public long getLastUpdateTick() {
    return lastTickCalChecked;
  }

  @Override
  public void setLastUpdateTick(long l) {
    lastTickCalChecked = serializeNBT().getLong("lastTickCalChecked");
    markDirty();
  }

  @Override
  public void setValidity(boolean approvalStatus, int tier) {
    isClimateValid = approvalStatus;
    this.tier = tier;
    markForSync();
  }

  protected boolean canGrow(int slot) {
    PlanterRecipe recipe = getRecipe(slot);
    return isClimateValid && recipe != null && getStage(slot) < PlanterRecipe.getMaxStage(recipe) &&
           tier >= PlanterRecipe.getTier(recipe) && world.getBlockState(pos).getValue(StatePropertiesFL.WET) && GreenhouseHelpers.isSkylightValid(world, pos);
  }

  public PlanterRecipe getRecipe(int slot) {
    ItemStack input = inventory.getStackInSlot(slot);
    PlanterRecipe recipe = null;
    if (!input.isEmpty()) {
      recipe = PlanterRecipe.get(input);
    }
    return recipe;
  }

  public boolean tryHarvest(EntityPlayer player, int slot) {
    PlanterRecipe recipe = getRecipe(slot);
    if (recipe != null && PlanterRecipe.getMaxStage(recipe) == getStage(slot)) {
      ItemStack returnStack = recipe.getOutputItem(inventory.getStackInSlot(slot));
      ItemHandlerHelper.giveItemToPlayer(player, returnStack);
      final int seeds = 1 + Constants.RNG.nextInt(2);
      ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(inventory.getStackInSlot(slot).getItem(), seeds));
      inventory.setStackInSlot(slot, ItemStack.EMPTY);
      stages[slot] = 0;
      resetCounter();
      markForSync();
      markForBlockUpdate();
      return true;
    }
    return false;
  }

  public int getStage(int slot) {
    return stages[slot];
  }

  @Override
  public void setWater(int amount) {
    waterUses = amount;
  }

  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
    super.onDataPacket(net, packet);
    markForBlockUpdate();
  }
}
