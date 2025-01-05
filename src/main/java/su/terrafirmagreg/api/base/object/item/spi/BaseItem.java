package su.terrafirmagreg.api.base.object.item.spi;


import su.terrafirmagreg.api.base.object.item.api.IItemSettings;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import lombok.Getter;

@Getter
public abstract class BaseItem extends Item implements IItemSettings {

  protected final Settings settings;

  public BaseItem() {
    this.settings = Settings.of();
  }

  public BaseItem(Settings settings) {
    this.settings = settings;
  }

  @Override
  public String getTranslationKey(ItemStack stack) {
    return this.getTranslationKey();
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize("item", this.getRegistryName());
  }


}
