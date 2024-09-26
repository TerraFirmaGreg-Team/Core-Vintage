package net.dries007.tfc.objects.items;

import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.food.IItemFoodTFC;

import net.minecraft.item.ItemFood;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

public class ItemFoodFL extends ItemFood implements IItemFoodTFC {

  public FoodData data;

  public ItemFoodFL(FoodData data) {
    super(0, 0.0F, false);
    this.setMaxDamage(0);
    this.data = data;
  }

  @Override
  public ICapabilityProvider getCustomFoodHandler() {
    return new FoodHandler(null, data);
  }
}
