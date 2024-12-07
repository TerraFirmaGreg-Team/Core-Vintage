package com.eerussianguy.firmalife.te;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.FoodTrait;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.objects.te.TEInventory;
import net.dries007.tfc.util.calendar.CalendarTFC;

import javax.annotation.Nonnull;

public class TEString extends TEInventory {

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

  public long getTicksSinceUpdate() {
    return CalendarTFC.PLAYER_TIME.getTicks() - lastUpdateTick;
  }

  public void resetCounter() {
    lastUpdateTick = CalendarTFC.PLAYER_TIME.getTicks();
    markForSync();
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

  @Nonnull
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
