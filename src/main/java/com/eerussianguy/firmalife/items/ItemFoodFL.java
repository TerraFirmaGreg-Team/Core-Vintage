package com.eerussianguy.firmalife.items;

import su.terrafirmagreg.api.base.object.item.spi.BaseItemFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityProviderFood;
import su.terrafirmagreg.modules.core.capabilities.food.IItemFoodTFC;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemFoodFL extends BaseItemFood implements IItemFoodTFC {

  public FoodData data;

  public ItemFoodFL(FoodData data) {
    super(0, 0.0F, false);
    this.setMaxDamage(0);
    this.data = data;
  }

  @Override
  public ICapabilityProvider getCustomFoodHandler() {
    return new CapabilityProviderFood(null, data);
  }
}
