package net.dries007.tfc.objects.items;

import su.terrafirmagreg.api.base.object.item.spi.BaseItemFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityProviderFoodHeat;
import su.terrafirmagreg.modules.core.capabilities.food.IItemFoodTFC;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemHeatableFoodFL extends BaseItemFood implements IItemFoodTFC {

  public FoodData data;

  public ItemHeatableFoodFL(FoodData data) {
    super(0, 0.0F, false);
    this.setMaxDamage(0);
    this.data = data;
  }

  @Override
  public ICapabilityProvider getCustomFoodHandler() {
    return new CapabilityProviderFoodHeat(null, data, 1.0F, 200.0F);
  }
}
