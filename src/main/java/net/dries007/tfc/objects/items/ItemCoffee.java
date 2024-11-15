package net.dries007.tfc.objects.items;

import su.terrafirmagreg.api.registry.provider.IProviderModel;

import net.minecraft.item.Item;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.caffeineaddon.CaffeineAddon;
import net.dries007.caffeineaddon.init.ModItems;

public class ItemCoffee extends Item implements IProviderModel {

  public ItemCoffee(String name) {
    super();
    this.setTranslationKey(name);
    this.setRegistryName(name);
    this.setCreativeTab(CreativeTabsTFC.CT_FOOD);

    ModItems.ITEMS.add(this);
  }

  @Override
  public void onModelRegister() {
    CaffeineAddon.proxy.registerItemRenderer(this, 0, "inventory");
  }

}
