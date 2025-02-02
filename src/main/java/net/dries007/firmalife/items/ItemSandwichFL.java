package net.dries007.firmalife.items;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.objects.items.food.ItemSandwich;

import javax.annotation.Nullable;

public class ItemSandwichFL extends ItemFoodFL {

  private final FoodData data;

  public ItemSandwichFL(FoodData data) {
    super(data);
    this.data = data;
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new ItemSandwich.SandwichHandler(nbt, data);
  }
}
