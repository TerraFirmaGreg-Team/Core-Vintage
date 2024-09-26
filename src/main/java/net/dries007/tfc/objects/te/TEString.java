package net.dries007.tfc.objects.te;

import su.terrafirmagreg.api.base.tile.BaseTileInventory;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;

public class TEString extends BaseTileInventory {

  private long lastUpdateTick;

  public TEString() {
    super(1);
  }

  public void tryCook() {
    ItemStack input = inventory.getStackInSlot(0);
    HeatRecipe recipe = HeatRecipe.get(input);
    ItemStack output = recipe != null ? recipe.getOutputStack(input) : input.copy();
    CapabilityFood.updateFoodDecayOnCreate(output);
    CapabilityFood.applyTrait(output, FoodTrait.SMOKED);
    CapabilityFood.removeTrait(output, FoodTrait.BRINED);
    inventory.setStackInSlot(0, output);
    markForSync();
    resetCounter();
  }

  public void resetCounter() {
    lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
    markForSync();
  }

  public long getTicksSinceUpdate() {
    return Calendar.PLAYER_TIME.getTicks() - lastUpdateTick;
  }

  public void reduceCounter(long amount) {
    lastUpdateTick += amount;
    markForSync();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    lastUpdateTick = nbt.getLong("tick");
    super.readFromNBT(nbt);
  }

  @NotNull
  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setLong("tick", lastUpdateTick);
    return super.writeToNBT(nbt);
  }

  @Override
  public int getSlotLimit(int slot) {
    return 1;
  }
}
